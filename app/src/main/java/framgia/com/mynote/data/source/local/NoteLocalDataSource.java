package framgia.com.mynote.data.source.local;

import framgia.com.mynote.data.model.Callback;
import framgia.com.mynote.data.source.NoteDataSource;

public class NoteLocalDataSource implements NoteDataSource.Local {
    private static NoteLocalDataSource sInstance;

    private NoteLocalDataSource() {
    }

    public static NoteLocalDataSource getInstance() {
        if (sInstance == null) {
            synchronized (NoteLocalDataSource.class) {
                sInstance = new NoteLocalDataSource();
            }
        }
        return sInstance;
    }

    @Override
    public void getNotes(Callback callback) {
       new NotesAsynctask(callback).execute();
    }
}
