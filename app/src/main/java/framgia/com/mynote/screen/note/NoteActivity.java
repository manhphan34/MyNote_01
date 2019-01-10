package framgia.com.mynote.screen.note;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteBinding;
import framgia.com.mynote.screen.detail_note.DetailNoteActivity;
import framgia.com.mynote.screen.edit.NoteUpdateActivity;


/**
 * Note Screen.
 */
public class NoteActivity extends AppCompatActivity {
    private NoteViewModel mViewModel;
    private ActivityNoteBinding mBinding;
    private List<Note> mNotes;
    private NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);
        initToolBar();
        initRecyclerView();
        initMutableLiveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_note, menu);
        MenuItem searchItem = menu.findItem(R.id.search_home);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mViewModel.getNotesByKey(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mViewModel.getNotesByKey(s);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }

    public void initToolBar() {
        setSupportActionBar(mBinding.toolBarInclude.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textTitle = mBinding.toolBarInclude.textTitle;
        textTitle.setText(getResources().getString(R.string.app_name));
    }

    public void initRecyclerView() {
        mNotes = new ArrayList<>();
        mBinding.recyclerNote.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerNote.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mBinding.recyclerNote.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NotesAdapter(this, mNotes, mViewModel);
        mBinding.recyclerNote.setAdapter(mAdapter);
    }

    public void initMutableLiveData() {
        mViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                mAdapter.addData(notes);
            }
        });
        mViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showToast(s);
            }
        });
        mViewModel.getSuccessMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showToast(s);
            }
        });
        mViewModel.getOpenNoteEvent().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                openNoteDetails(note);
            }
        });
        mViewModel.getDeleteNoteEvent().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {

            }
        });
        mViewModel.getEditNoteEvent().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                openNoteUpdate(note);
            }
        });
        mViewModel.getAddNoteToHomeScreenEvent().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {

            }
        });
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void openNoteDetails(Note note) {
        startActivity(DetailNoteActivity.getDetailNoteIntent(this, note));
    }

    public void openNoteUpdate(Note note) {
        startActivity(NoteUpdateActivity.getUpdateActivity(this, note));
    }
}
