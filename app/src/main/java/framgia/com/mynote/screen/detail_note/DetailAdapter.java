package framgia.com.mynote.screen.detail_note;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.databinding.ItemTaskBinding;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>
        implements TaskItemUserActionListener {
    private List<Task> mTasks;
    private DetailViewModel mViewModel;
    private LayoutInflater mInflater;

    public DetailAdapter(Context context, List<Task> tasks, DetailViewModel viewModel) {
        mViewModel = viewModel;
        mInflater = LayoutInflater.from(context);
        mTasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTaskBinding binding = DataBindingUtil
                .inflate(mInflater, R.layout.item_task, viewGroup, false);
        binding.textTaskTitle.setFocusable(false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mBinding.setTask(mTasks.get(i));
        viewHolder.mBinding.setListener(this);
    }

    @Override
    public int getItemCount() {
        return mTasks == null ? 0 : mTasks.size();
    }

    @Override
    public void onCheckedChanged(Task task, boolean isChecked) {
        task.setDone(isChecked ? 1 : 0);
        mViewModel.getCheckBoxTaskEvent().setValue(task);
        mViewModel.onCheckedChanged(task);
    }

    @Override
    public void onDeleteTaskClicked(Task task) {
        mViewModel.getDeleteTaskEvent().setValue(task);
        mViewModel.onDeleteTaskClicked(task);
    }

    public void addData(List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        mTasks.clear();
        mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskBinding mBinding;

        public ViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
