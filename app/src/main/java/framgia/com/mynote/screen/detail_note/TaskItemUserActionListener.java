package framgia.com.mynote.screen.detail_note;

import framgia.com.mynote.data.model.Task;

public interface TaskItemUserActionListener {
    void onCheckedChanged(Task task, boolean isChecked);

    void onDeleteTaskClicked(Task task);
}
