package framgia.com.mynote.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import framgia.com.mynote.data.model.Task;
import io.reactivex.Flowable;

@Dao
public interface TaskDAO {
    String SQL_GET_ALL_TASK_OF_NOTE =
            "SELECT * FROM " + Task.TABLE_NAME + " WHERE " + Task.NOTE_ID + " = :nodeId";

    String SQL_DELETE_ALL_TASK_OF_NOTE =
            "DELETE FROM " + Task.TABLE_NAME + " WHERE " + Task.NOTE_ID + " = :noteId";

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query(SQL_DELETE_ALL_TASK_OF_NOTE)
    void deleteAllTaskOfNote(int noteId);

    @Query(SQL_GET_ALL_TASK_OF_NOTE)
    Flowable<List<Task>> getAllTaskOfNote(int nodeId);
}
