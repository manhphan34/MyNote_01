package framgia.com.mynote.data.source.local.note;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.data.source.local.dao.NoteDAO;
import framgia.com.mynote.data.source.local.dao.TaskDAO;

import static framgia.com.mynote.data.source.local.note.NoteDatabase.DATABASE_VERSION;

@Database(entities = {Note.class, Task.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Note.db";
    private static NoteDatabase sInstance;

    public abstract NoteDAO noteDao();

    public abstract TaskDAO taskDAO();

    public static NoteDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (NoteDatabase.class) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class,
                        DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }
}
