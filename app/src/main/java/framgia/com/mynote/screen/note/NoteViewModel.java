package framgia.com.mynote.screen.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
    private SingleLiveEvent<String> mErrorMessage = new SingleLiveEvent<>();
    private CompositeDisposable mCompositeDisposable;
    private NoteDatabase mNoteDatabase;
    private SingleLiveEvent<Note> mOpenNoteEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Note> mDeleteNoteEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Note> mEditNoteEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Note> mAddNoteToHomeScreenEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> mSuccessMessage = new SingleLiveEvent<>();

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

    public SingleLiveEvent<String> getErrorMessage() {
        return mErrorMessage;
    }

    public SingleLiveEvent<Note> getOpenNoteEvent() {
        return mOpenNoteEvent;
    }

    public SingleLiveEvent<Note> getDeleteNoteEvent() {
        return mDeleteNoteEvent;
    }

    public SingleLiveEvent<Note> getEditNoteEvent() {
        return mEditNoteEvent;
    }

    public SingleLiveEvent<Note> getAddNoteToHomeScreenEvent() {
        return mAddNoteToHomeScreenEvent;
    }

    public SingleLiveEvent<String> getSuccessMessage() {
        return mSuccessMessage;
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

    public void deleteNote(final Note note) {
        Observable<Boolean> deleteTask = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mRepository.deleteAllTaskOfNote(note.getId());
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
        Observable<Boolean> deleteNote = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mRepository.deleteNote(note);
                emitter.onNext(true);
            }
        });
        Observable.merge(deleteTask, deleteNote)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mSuccessMessage.setValue(getApplication().
                                getResources().getString(R.string.msg_delete_note_success));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorMessage.setValue(throwable.getMessage());
                    }
                });
    }

    public void getNotesByKey(String s) {
        String percentChar = getApplication()
                .getResources()
                .getString(R.string.char_percent);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(percentChar).append(s).append(percentChar);
        Disposable disposable = mRepository.getNotesByKey(stringBuilder.toString())
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
}
