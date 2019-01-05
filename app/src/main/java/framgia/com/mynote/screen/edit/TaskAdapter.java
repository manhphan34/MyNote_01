package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.ItemTaskBinding;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> mTasks;

    public TaskAdapter(Context context) {
        mContext = context;
        mTasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTaskBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.onBind(mTasks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    public void addData(List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        int position = 0;
        if (getItemCount() != 0) {
            position = mTasks.size() - 1;
        }
        mTasks.addAll(tasks);
        notifyItemRangeChanged(position, getItemCount());
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskBinding mBinding;

        public TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(Task task) {
            mBinding.setTask(task);
            mBinding.executePendingBindings();
        }
    }
}
