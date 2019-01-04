package framgia.com.mynote.screen.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.data.model.Callback;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.NoteLocalDataSource;


/**
 * Exposes the data to be used in the Note screen.
 */

public class NoteViewModel extends AndroidViewModel implements Callback {
    private NoteRepository mRepository;
    private MutableLiveData<List<Note>> mNotes;
    private MutableLiveData<String> mErrorMessage;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = NoteRepository.getInstance(NoteLocalDataSource.getInstance());
    }

    public MutableLiveData<List<Note>> getNotes() {
        if (mNotes == null) {
            mNotes = new MutableLiveData<>();
            mRepository.getNotes(this);
        }
        return mNotes;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (mErrorMessage == null) {
            mErrorMessage = new MutableLiveData<>();
        }
        return mErrorMessage;
    }

    @Override
    public void onGetDataSuccess(List<Note> notes) {
        mNotes.setValue(notes);
    }

    @Override
    public void onGetDataFailed(Exception e) {
        mErrorMessage.setValue(e.getMessage());
    }
}
