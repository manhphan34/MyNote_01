package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.ItemTaskBinding;
import framgia.com.mynote.databinding.ItemTaskNoteUpdateBinding;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> mTasks;
    private HandlerClick.TaskHandleListener mHandleListener;

    public TaskAdapter(Context context, HandlerClick.TaskHandleListener handleListener) {
        mContext = context;
        mTasks = new ArrayList<>();
        mHandleListener = handleListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTaskNoteUpdateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_task_note_update, viewGroup, false);
        return new TaskViewHolder(binding, mHandleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.onBind(mTasks.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    public void replaceData(List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        if (mTasks.size() > 0) {
            mTasks.removeAll(mTasks);
        }
        mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements
            CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        private ItemTaskNoteUpdateBinding mBinding;
        private Task mTask;
        private int mPosition;
        private HandlerClick.TaskHandleListener mHandleListener;

        public TaskViewHolder(ItemTaskNoteUpdateBinding binding, HandlerClick.TaskHandleListener handleListener) {
            super(binding.getRoot());
            mBinding = binding;
            mHandleListener = handleListener;
            onTextChange();
        }

        public void onBind(Task task, int position) {
            mBinding.setTask(task);
            mPosition = position;
            mTask = task;
            mBinding.imageButton.setOnClickListener(this);
            mBinding.checkBoxTask.setOnCheckedChangeListener(this);
            mBinding.executePendingBindings();
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mTask.setDone(isChecked ? 1 : 0);
            mHandleListener.onStatusChange(mPosition, mTask);
        }

        @Override
        public void onClick(View v) {
            mHandleListener.onDeleteTask(mPosition);
        }

        private void onTextChange() {
            mBinding.textTaskTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mTask.setTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            if (!mBinding.textTaskTitle.isFocusable()){
                mHandleListener.onTitleChange(mPosition, mTask);
            }
        }
    }
}