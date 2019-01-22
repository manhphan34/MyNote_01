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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Audio;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;
import framgia.com.mynote.screen.edit.dialog.AudioChooserDialog;
import framgia.com.mynote.screen.edit.dialog.AudioChooserOptionDialog;
import framgia.com.mynote.screen.edit.dialog.AudioCreatorDialog;
import framgia.com.mynote.screen.edit.dialog.DatePickerDialog;
import framgia.com.mynote.screen.edit.dialog.ImageChooserDialog;
import framgia.com.mynote.screen.edit.dialog.LocationChooserDialog;
import framgia.com.mynote.screen.edit.dialog.TimePickerDialog;
import framgia.com.mynote.utils.DateTimeUtil;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.FileHelper;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Permission;

public class NoteUpdateActivity extends AppCompatActivity implements HandlerClick.AudioHandledClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, HandlerClick.ImageHandledClickListener,
        HandlerClick.LocationHandledListener, HandlerClick.DatePickerHandledClickListener,
        HandlerClick.TimePickerHandledClickListener, HandlerClick.TaskHandleListener {
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
    private TimePickerDialog mTimePickerDialog;
    private AudioChooserDialog mAudioChooserDialog;
    private AudioCreatorDialog mAudioCreatorDialog;
    private List<Task> mTasks;

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
        mUpdateViewModel.onDestroy();
        DialogHelper.release();
        super.onDestroy();
    }

    @Override
    public void onPlayAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_on);
    }

    @Override
    public void onPlayAudioFailed() {
        Toast.makeText(getApplicationContext(), R.string.audio_load_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStopAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_off);
    }

    @Override
    public void onSelectedAudio(Audio audio) {
        if (mMedia.isPlaying()) {
            mMedia.stop();
        }
        mAudioChooserDialog.dismiss();
        mUpdateViewModel.getAudio().setValue(audio.getPath());
    }

    @Override
    public void onRecordAudioStop(String path) {
        mAudioCreatorDialog.dismiss();
        showDialogSaveAudio(path);
    }

    @Override
    public void onRecordAudioStart() {
        try {
            mAudioCreatorDialog.record(mUpdateViewModel.getAudio().getValue());
            Toast.makeText(this, getString(R.string.message_recording), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            onRecordAudioFail();
        }
    }

    @Override
    public void onRecordAudioFail() {
        Toast.makeText(this, R.string.error_recoding_audio, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecorded(String path) {
        mUpdateViewModel.getAudio().setValue(path);
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
                case Permission.REQUEST_RECORD_AUDIO_PERMISSION:
                    showAudioOptionDialog();
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
                break;
            case R.id.navigation_alarm:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), this);
                datePickerDialog.showDialog();
                break;
            case R.id.navigation_audio:
                requestPermissionAudio();
            case R.id.navigation_task:
                mTasks.add(new Task(mUpdateViewModel.getId().getValue(), getString(R.string.taks_title_default), 0));
                mUpdateViewModel.getTasks().setValue(mTasks);
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
            case PICK_IMAGE_FROM_GALLERY:
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    pickImage(bitmap);
                    break;
                } catch (IOException e) {
                    onSaveImageFail();
                }
            case PICK_IMAGE_FROM_CAMERA:
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
        mLocationDialog.dismiss();
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


    @Override
    public void onChooseDate(long time) {
        mTimePickerDialog = new TimePickerDialog(this, this);
        mTimePickerDialog.showDialog();
        mUpdateViewModel.getTime().setValue(time);
    }

    @Override
    public void onChooseDateError(Exception e) {
        Toast.makeText(this, R.string.error_system_busy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChooseHour(long time) {
        mTimePickerDialog.dismiss();
        mUpdateViewModel.getTime().setValue(getTime(mUpdateViewModel.getTime().getValue() + time));
    }

    @Override
    public void onChooseHourError(Exception e) {
        Toast.makeText(this, R.string.error_system_busy, Toast.LENGTH_SHORT).show();
    }

    private void pickImage(Bitmap bitmap) {
        saveImage(bitmap);
    }

    private void saveImage(Bitmap bitmap) {
        try {
            FileHelper fileHelper = new FileHelper();
            if (mUpdateViewModel.getImageUrl().getValue() != null) {
                fileHelper.deleteFile(new File(mUpdateViewModel.getImageUrl().getValue()));
            }
            File file = initFile();
            fileHelper.createFile(file);
            fileHelper.writeFileImage(file, bitmap);
            mOptionImage.dismiss();
            mUpdateViewModel.getImageUrl().setValue(file.getPath());
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
        mMedia = new MediaNoteUpdate(this, this);
        initToolbar(mBinding);
        mUpdateViewModel = ViewModelProviders.of(this).get(NoteUpdateViewModel.class);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mBinding.setLifecycleOwner(this);
        mOptionImage = new ImageChooserDialog(this, this);
        mAudioChooserDialog = new AudioChooserDialog(this, this);
        mAudioCreatorDialog = new AudioCreatorDialog(this, this);
        initRecycle();
        mUpdateViewModel.setTaskAdapter(new TaskAdapter(this, this));
        FusedLocationProviderClient providerClient = new FusedLocationProviderClient(this);
        mLocationDialog = new LocationChooserDialog(this, this, providerClient);
    }

    private void initData() {
        mNote = getNote();
        mTasks = new ArrayList<>();
        mBinding.setViewModel(mUpdateViewModel);
        mBinding.setMedia(mMedia);
        mUpdateViewModel.setData(mNote);
        getTasks();
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

    private void requestPermissionAudio() {
        Permission permission = new Permission(this);
        if (!permission.requestPermissionAudio()) {
            showAudioOptionDialog();
        }
    }

    private void showAudioOptionDialog() {
        AudioChooserOptionDialog chooserOptionDialog =
                new AudioChooserOptionDialog(this, mAudioChooserDialog, mAudioCreatorDialog);
        chooserOptionDialog.showDialog();
    }

    private void onSaveImageFail() {
        Toast.makeText(this, getString(R.string.error_system_busy), Toast.LENGTH_SHORT).show();
    }

    private File initFile() {
        return new File(KeyUtils.PATH_FOLDER_IMAGE,
                Calendar.getInstance().getTimeInMillis() + KeyUtils.EXTEND_IMAGE);
    }


    private long getTime(long time) {
        return time + DateTimeUtil.TIME_COMPENSATION;
    }

    private void showDialogSaveAudio(String path) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_record_audio_succes)
                .setPositiveButton(R.string.button_save_title, (dialog, id)
                        -> mUpdateViewModel.getAudio().setValue(path))
                .setNegativeButton(R.string.button_cancle_title, null);
        builder.create().show();
    }

    private void getTasks() {
        mUpdateViewModel.getTasks().observe(this, tasks -> {
            mUpdateViewModel.getTaskAdapter().replaceData(tasks);
        });
    }

    private void initRecycle() {
        mBinding.recyclerTask.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(),
                        LinearLayoutManager.VERTICAL));
        mBinding.recyclerTask.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStatusChange(int pos, Task task) {
        updateTask(pos, task);
    }

    @Override
    public void onDeleteTask(int pos) {
        mTasks.remove(pos);
        mUpdateViewModel.getTasks().setValue(mTasks);
    }

    @Override
    public void onTitleChange(int pos, Task task) {
        updateTask(pos, task);
    }

    private void updateTask(int pos, Task task) {
        if (!mBinding.recyclerTask.isComputingLayout()) {
            mTasks.add(pos, task);
            mTasks.remove(pos++);
            mUpdateViewModel.getTasks().setValue(mTasks);
        }
    }
}
