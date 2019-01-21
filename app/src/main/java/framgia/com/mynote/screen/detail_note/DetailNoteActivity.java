package framgia.com.mynote.screen.detail_note;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.ActivityDetailNoteBinding;
import framgia.com.mynote.screen.note.NoteActivity;

public class DetailNoteActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE = "EXTRA_NOTE";
    private DetailViewModel mViewModel;
    private ActivityDetailNoteBinding mBinding;
    private DetailAdapter mAdapter;
    private List<Task> mTasks;

    public static Intent getDetailNoteIntent(Context context, Note note) {
        Intent intent = new Intent(context, DetailNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_note);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setNote(this.getIntent().getParcelableExtra(EXTRA_NOTE));
        mViewModel.getNote().setValue(this.getIntent().getParcelableExtra(EXTRA_NOTE));
        initToolBar();
        initRecyclerView();
        initMutableLiveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return true;
    }

    public void initMutableLiveData() {
        mViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mAdapter.addData(tasks);
            }
        });
        mViewModel.getCheckBoxTaskEvent().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                StringBuilder stringBuilder = new StringBuilder(
                        getApplication().getString(R.string.msg_update_task));
                stringBuilder.append(task.getTitle());
                stringBuilder.append(getApplication().getString(R.string.msg_success));
                showToast(stringBuilder.toString());
            }
        });
        mViewModel.getDeleteTaskEvent().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                StringBuilder stringBuilder = new StringBuilder(getString(R.string.msg_delete_task));
                stringBuilder.append(task.getTitle());
                stringBuilder.append(getString(R.string.msg_success));
                showToast(stringBuilder.toString());
            }
        });
        mViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showToast(s);
            }
        });
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void initRecyclerView() {
        mTasks = new ArrayList<>();
        mBinding.recyclerDetail.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerDetail.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mBinding.recyclerDetail.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DetailAdapter(this, mTasks, mViewModel);
        mBinding.recyclerDetail.setAdapter(mAdapter);
    }

    public void initToolBar() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(NoteActivity.getNoteIntent(this, Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
