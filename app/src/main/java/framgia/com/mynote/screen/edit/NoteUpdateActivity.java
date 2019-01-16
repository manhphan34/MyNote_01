package framgia.com.mynote.screen.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;
import framgia.com.mynote.screen.edit.dialog.ImageChooserDialog;
import framgia.com.mynote.screen.edit.dialog.LocationChooserDialog;
import framgia.com.mynote.utils.FileHelper;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Permission;

public class NoteUpdateActivity extends AppCompatActivity implements HandlerClick.AudioHandledClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, HandlerClick.ImageHandledClickListener,
        HandlerClick.LocationHandledListener {
    public static final String EXTRA_NOTE = "EXTRA_NOTE";
    public static final float TEXT_TOOL_BAR_SIZE = 18;
    public static final int PICK_IMAGE_FROM_GALLERY = 0x248;
    public static final int PICK_IMAGE_FROM_CAMERA = 0x249;
    public static final String ACTION_PICK = Intent.ACTION_PICK;
    public static final String DATA_FROM_CAMERA = "data";
    public static final String ACTION_IMAGE_CAPTURE = MediaStore.ACTION_IMAGE_CAPTURE;
    public static final Uri EXTERNAL_CONTENT_URI = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private ActivityNoteDetailBinding mBinding;
    private NoteUpdateViewModel mUpdateViewModel;
    private Note mNote;
    private MediaNoteUpdate mMedia;
    private ImageChooserDialog mOptionImage;
    private LocationChooserDialog mLocationDialog;

    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, NoteUpdateActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mMedia.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPlayAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_off);
    }

    @Override
    public void onPlayAudioFailed() {
        Toast.makeText(getApplicationContext(), R.string.audio_load_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStopAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_on);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && isGranted(grantResults)) {
            switch (requestCode) {
                case Permission.REQUEST_RECORD_IMAGE_PERMISSION:
                    mOptionImage.showDialog();
                    return;
                case Permission.REQUEST_LOCATION_PERMISSION:
                    mLocationDialog.showDialog();
                    return;
            }
        }
        onPermissionFail();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_image:
                requestPermissionImage();
                break;
            case R.id.navigation_location:
                requestPermissionLocation();
            case R.id.navigation_alarm:
                break;
        }
        return false;
    }


    @Override
    public void onChooseImageFromGallery() {
        startActivityForResult(new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI),
                PICK_IMAGE_FROM_GALLERY);
    }

    @Override
    public void onCreateNewImage() {
        startActivityForResult(new Intent(ACTION_IMAGE_CAPTURE),
                PICK_IMAGE_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case PICK_IMAGE_FROM_CAMERA:
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    pickImage(bitmap);
                    break;
                } catch (IOException e) {
                    onSaveImageFail();
                }
            case PICK_IMAGE_FROM_GALLERY:
                pickImage((Bitmap) data.getExtras().get(DATA_FROM_CAMERA));
                break;
        }
    }

    @Override
    public void onGPSTurnOff() {
        Toast.makeText(getApplicationContext(), getString(R.string.message_turn_on), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetWorkTurnOff() {
        Toast.makeText(getApplicationContext(), getString(R.string.message_turn_on_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetLocationSuccess(String location) {
        mLocationDialog.dissmiss();
        mUpdateViewModel.getLocation().setValue(location);
    }

    @Override
    public void onGEtLocationFail(Exception e) {
        Toast.makeText(getApplicationContext(), R.string.error_system_busy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationEmpty() {
        Toast.makeText(getApplicationContext(), getString(R.string.error_location_null), Toast.LENGTH_SHORT).show();
    }

    private void pickImage(Bitmap bitmap) {
        saveImage(bitmap);
    }

    private void saveImage(Bitmap bitmap) {
        try {
            FileHelper fileHelper = new FileHelper();
            if (mUpdateViewModel.getImage().getValue() != null) {
                fileHelper.deleteFile(new File(mUpdateViewModel.getImage().getValue()));
            }
            File file = initFile();
            fileHelper.createFile(file);
            fileHelper.writeFileImage(file, bitmap);
            mOptionImage.dissmiss();
            mUpdateViewModel.getImage().setValue(file.getPath());
        } catch (IOException e) {
            onSaveImageFail();
        }
    }

    private void initToolbar(ActivityNoteDetailBinding binding) {
        setSupportActionBar(binding.toolBarInclude.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textTitle = binding.toolBarInclude.textTitle;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textTitle.setTextSize(TEXT_TOOL_BAR_SIZE);
        textTitle.setText(getString(R.string.tool_bar_title));
    }

    private void event() {
        initView();
        initData();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_detail);
        mMedia = new MediaNoteUpdate(mNote, this);
        initToolbar(mBinding);
        mUpdateViewModel = ViewModelProviders.of(this).get(NoteUpdateViewModel.class);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mBinding.setLifecycleOwner(this);
        mOptionImage = new ImageChooserDialog(this, this);
        FusedLocationProviderClient providerClient = new FusedLocationProviderClient(this);
        mLocationDialog = new LocationChooserDialog(this, this, providerClient);
    }

    private void initData() {
        mNote = getNote();
        mBinding.setViewModel(mUpdateViewModel);
        mBinding.setMedia(mMedia);
        mUpdateViewModel.setData(mNote);
    }

    private Note getNote() {
        if (this.getIntent() != null) {
            return this.getIntent().getParcelableExtra(EXTRA_NOTE);
        }
        return new Note.Builder().build();
    }

    private boolean isGranted(int[] grantResults) {
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void onPermissionFail() {
        Toast.makeText(this, getString(R.string.permission_granted_fail), Toast.LENGTH_SHORT).show();
    }

    private void requestPermissionImage() {
        Permission permission = new Permission(this);
        if (!permission.requestPermissionImage()) {
            mOptionImage.showDialog();
        }
    }

    private void requestPermissionLocation() {
        Permission permission = new Permission(this);
        if (!permission.requestPermissionLocation()) {
            mLocationDialog.showDialog();
        }
    }

    private void onSaveImageFail() {
        Toast.makeText(this, getString(R.string.error_system_busy), Toast.LENGTH_SHORT).show();
    }

    private File initFile() {
        return new File(KeyUtils.PATH_FOLDER_IMAGE,
                Calendar.getInstance().getTimeInMillis() + KeyUtils.EXTEND_IMAGE);
    }
}
