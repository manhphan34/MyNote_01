package framgia.com.mynote.data.source;

import framgia.com.mynote.data.model.Callback;

public interface NoteDataSource {
    interface Local {
        void getNotes(Callback callback);
    }
}
