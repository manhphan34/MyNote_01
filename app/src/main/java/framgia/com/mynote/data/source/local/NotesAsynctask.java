package framgia.com.mynote.data.source.local;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import java.util.List;

import framgia.com.mynote.data.model.Callback;
import framgia.com.mynote.data.model.Note;

public class NotesAsynctask extends AsyncTask<Void, Void, List<Note>> {
    private Callback mCallback;
    private Exception mException;
    private List<Note> mNotes;

    public NotesAsynctask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Note> doInBackground(Void... voids) {
        return getNotes();
    }

    private List<Note> getNotes() {
        try {

        } catch (SQLiteException e) {
            mException = e;
        }
        return mNotes;
    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        super.onPostExecute(notes);
        if (mException == null) {
            mCallback.onGetDataSuccess(notes);
            return;
        }
        mCallback.onGetDataFailed(mException);
    }
}
