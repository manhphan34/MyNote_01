package framgia.com.mynote.screen.edit;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.FragmentTaskBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    public static final String ARGUMENT_NOTE = "ARGUMENT_NOTE";
    private TaskAdapter mTaskAdapter;
    private TaskViewModel mTaskViewModel;
    private FragmentTaskBinding mTaskBinding;
    private Note mNote;

    public static TaskFragment getInstance(Note note) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        initData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTaskViewModel.onDetach();
    }

    private void initComponent(View view) {
        mTaskAdapter = new TaskAdapter(getActivity().getApplicationContext());
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskBinding = DataBindingUtil.bind(view);
        initRecycle();
    }

    public TaskAdapter getTaskAdapter() {
        return mTaskAdapter;
    }

    private void initData() {
        mNote = getNote();
        if (mTaskBinding != null) {
            mTaskBinding.setLifecycleOwner(this);
            mTaskBinding.setTaskFragment(this);
        }
        getData();
    }

    private void initRecycle() {
        mTaskBinding.recyclerTask.addItemDecoration(
                new DividerItemDecoration(getActivity().getApplicationContext(),
                        LinearLayoutManager.VERTICAL));
        mTaskBinding.recyclerTask.setItemAnimator(new DefaultItemAnimator());
    }

    private void getData() {
        if (mNote != null) {
            mTaskViewModel.getTasks(mNote.getId()).observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    mTaskAdapter.addData(tasks);
                }
            });
        }
    }

    private Note getNote() {
        if (this.getArguments() != null) {
            mNote = this.getArguments().getParcelable(ARGUMENT_NOTE);
        }
        return mNote;
    }
}
