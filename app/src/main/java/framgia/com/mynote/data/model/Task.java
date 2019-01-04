package framgia.com.mynote.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = Task.TABLE_NAME,
        foreignKeys = @ForeignKey(entity = Note.class,
                parentColumns = Note.ID,
                childColumns = Task.NOTE_ID,
                onDelete = ForeignKey.CASCADE))
public class Task {
    public static final String TABLE_NAME = "TASK";
    public static final String TASK_ID = "ID";
    public static final String NOTE_ID = "NOTE_ID";
    public static final String TASK_TITLE = "TASK_TITLE";
    public static final String IS_DONE = "IS_DONE";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TASK_ID)
    private int mId;
    @ColumnInfo(name = NOTE_ID)
    private int mNoteId;
    @ColumnInfo(name = TASK_TITLE)
    private String mTitle;
    @ColumnInfo(name = IS_DONE)
    private int mDone;

    public Task() {
    }

    @Ignore
    public Task(int id, int noteId, String title, int done) {
        mId = id;
        mNoteId = noteId;
        mTitle = title;
        mDone = done;
    }

    @Ignore
    public Task(int noteId, String title, int done) {
        mNoteId = noteId;
        mTitle = title;
        mDone = done;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getNoteId() {
        return mNoteId;
    }

    public void setNoteId(int noteId) {
        mNoteId = noteId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getDone() {
        return mDone;
    }

    public void setDone(int done) {
        mDone = done;
    }
}
