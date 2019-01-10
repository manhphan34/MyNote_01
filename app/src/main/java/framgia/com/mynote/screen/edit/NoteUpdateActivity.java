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

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;

public class NoteUpdateActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE = "EXTRA_NOTE";

    public static Intent getUpdateActivity(Context context, Note note) {
        Intent intent = new Intent(context, NoteUpdateActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

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

    private void initView() {
        ActivityNoteDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_detail);
        initToolbar(binding);
    }

    private void initToolbar(ActivityNoteDetailBinding binding) {
        setSupportActionBar(binding.toolBarInclude.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textTitle = binding.toolBarInclude.textTitle;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textTitle.setText(getString(R.string.tool_bar_title));
    }
}
