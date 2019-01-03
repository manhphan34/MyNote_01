package framgia.com.mynote.data.repository;

import framgia.com.mynote.data.model.Callback;
import framgia.com.mynote.data.source.NoteDataSource;

public class NoteRepository implements NoteDataSource.Local {
    private NoteDataSource.Local mLocal;
    private static NoteRepository sInstance;

    private NoteRepository(NoteDataSource.Local local) {
        mLocal = local;
    }

    public static NoteRepository getInstance(NoteDataSource.Local local) {
        if (sInstance == null) {
            synchronized (NoteRepository.class) {
                sInstance = new NoteRepository(local);
            }
        }
        return sInstance;
    }

    @Override
    public void getNotes(Callback callback) {
        mLocal.getNotes(callback);
    }

}
