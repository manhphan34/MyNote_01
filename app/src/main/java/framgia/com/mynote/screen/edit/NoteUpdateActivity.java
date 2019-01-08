package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;
import framgia.com.mynote.databinding.DialogChooseOptionBinding;
import framgia.com.mynote.databinding.DialogCreateNewAudioBinding;
import framgia.com.mynote.utils.AudioHelper;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.Permission;

public class NoteUpdateActivity extends AppCompatActivity implements HandlerClick.AudioHandleClick {
    public static final String EXTRA_NOTE = "EXTRA_NOTE";
    public static final float TEXT_TOOL_BAR_SIZE = 18;
    private ActivityNoteDetailBinding mBinding;
    private Note mNote;
    private MediaNoteUpdate mMedia;
    private DialogHelper mDialog;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    public static Intent getUpdateActivity(Context context, Note note) {
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
        super.onDestroy();
        mMedia.onDestroy();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && isGranted(grantResults)) {
            switch (requestCode) {
                case Permission.REQUEST_RECORD_AUDIO_PERMISSION:
                    chooseAudioOption();
                    return;
            }
        }
        onPermissionFail();
    }

    @Override
    public void onPlayAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_off);
    }

    @Override
    public void onPlayAudioFailed() {
        Toast.makeText(getApplicationContext(), R.string.audio_load_fail, Toast.LENGTH_LONG).show();

    }

    public void showDialogRecordAudio() {
        DialogCreateNewAudioBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.dialog_create_new_audio, null, false);
        binding.setNoteUpdate(this);
        mDialog.initDialog(binding.getRoot(),
                DialogHelper.DIALOG_AUDIO_CREATE_WIDTH, DialogHelper.DIALOG_AUDIO_CREATE_HEIGHT);
        mDialog.setLocation(Gravity.CENTER);
        mDialog.show();
    }

    @Override
    public void onStopAudio() {
        mBinding.imageButtonSpeaker.setImageResource(R.drawable.ic_speaker_on);
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
        onBottomNavigationItemSelected();
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initToolbar(mBinding);
    }

    private void initData() {
        mNote = getNote();
        mBinding.setNote(mNote);
        mBinding.setMedia(mMedia);
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

    private void onBottomNavigationItemSelected() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_audio:
                        requestPermission();
                        break;
                }
                return false;
            }
        };
    }

    private void requestPermission() {
        Permission permission = new Permission(this);
        if (!permission.requestPermissionAudio()) {
            chooseAudioOption();
        }
    }

    private void chooseAudioOption() {
        DialogChooseOptionBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.dialog_choose_option, null, false);
        binding.setNoteUpdate(this);
        mDialog.initDialog(binding.getRoot(),
                DialogHelper.DIALOG_AUDIO_WIDTH, DialogHelper.DIALOG_AUDIO_HEIGHT);
        mDialog.setLocation(Gravity.BOTTOM);
        mDialog.show();
    }

    private void onPermissionFail() {
        Toast.makeText(this, getString(R.string.permission_granted_fail), Toast.LENGTH_SHORT).show();
    }
}
