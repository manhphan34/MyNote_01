package framgia.com.mynote.screen.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Exposes the data to be used in the Note screen.
 */

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private MutableLiveData<List<Note>> mNotes;
    private MutableLiveData<String> mErrorMessage;
    private CompositeDisposable mCompositeDisposable;
    private NoteDatabase mNoteDatabase;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNoteDatabase = NoteDatabase.getInstance(application);
        mRepository = NoteRepository
                .getInstance(NoteLocalDataSource
                        .getInstance(mNoteDatabase.noteDao(), mNoteDatabase.taskDAO()));
        mCompositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<List<Note>> getNotes() {
        if (mNotes == null) {
            mNotes = new MutableLiveData<>();
            getAllNote();
        }
        return mNotes;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (mErrorMessage == null) {
            mErrorMessage = new MutableLiveData<>();
        }
        return mErrorMessage;
    }

    public void getAllNote() {
        Disposable disposable = mRepository.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) {
                        mNotes.setValue(notes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorMessage.setValue(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void onDestroy() {
        if (mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
