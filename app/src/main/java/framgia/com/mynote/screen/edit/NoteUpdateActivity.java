package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;

public class NoteUpdateActivity extends AppCompatActivity implements HandlerClick.AudioHandleClick {
    public static final String EXTRA_NOTE = "EXTRA_NOTE";
    public static final float TEXT_TOOL_BAR_SIZE = 18;
    private ActivityNoteDetailBinding mBinding;
    private Note mNote;
    private MediaNoteUpdate mMedia;

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
}
