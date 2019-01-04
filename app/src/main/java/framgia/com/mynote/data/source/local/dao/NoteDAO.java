package framgia.com.mynote.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import framgia.com.mynote.data.model.Note;
import io.reactivex.Flowable;

@Dao
public interface NoteDAO {
    String SQL_GET_ALL_NOTE = "SELECT * FROM " + Note.TABLE_NAME;
    String SQL_GET_NOTES_BY_KEY =
            "SELECT * FROM " + Note.TABLE_NAME + " WHERE " + Note.NOTE_TITLE + " LIKE :key";
    String SQL_DELETE_ALL_NOTE = "DELETE FROM " + Note.TABLE_NAME;
    String SQL_GET_NOTES_HAS_TASK =
            "SELECT * FROM " + Note.TABLE_NAME + " WHERE " + Note.HAS_TASK + " = :hasTask";

    @Query(SQL_GET_ALL_NOTE)
    Flowable<List<Note>> getAllNote();

    @Query(SQL_GET_NOTES_BY_KEY)
    Flowable<List<Note>> getNotesByKey(String key);

    @Query(SQL_GET_NOTES_HAS_TASK)
    Flowable<List<Note>> getNotesHasTask(int hasTask);

    @Query(SQL_DELETE_ALL_NOTE)
    void deleteAllNote();

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
