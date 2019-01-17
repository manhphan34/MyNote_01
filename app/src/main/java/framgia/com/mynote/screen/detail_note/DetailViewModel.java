package framgia.com.mynote.screen.detail_note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import framgia.com.mynote.screen.note.SingleLiveEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {
    public static int ACTION_UPDATE = 0;
    public static int ACTION_DELETE = 1;
    private final NoteDatabase mNoteDatabase;
    private MutableLiveData<List<Task>> mTasks;
    private CompositeDisposable mCompositeDisposable;
    private NoteRepository mRepository;
    private MutableLiveData<Note> mNote;
    private SingleLiveEvent<String> mSuccessMessage = new SingleLiveEvent<>();
    private SingleLiveEvent<String> mErrorMessage = new SingleLiveEvent<>();
    private SingleLiveEvent<Task> mCheckBoxTaskEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Task> mDeleteTaskEvent = new SingleLiveEvent<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mNoteDatabase = NoteDatabase.getInstance(application);
        mRepository = NoteRepository
                .getInstance(NoteLocalDataSource
                        .getInstance(mNoteDatabase.noteDao(), mNoteDatabase.taskDAO()));
        mCompositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Note> getNote() {
        if (mNote == null) {
            mNote = new MutableLiveData<>();
        }
        return mNote;
    }

    public MutableLiveData<List<Task>> getTasks() {
        if (mTasks == null) {
            mTasks = new MutableLiveData<>();
            getAllTaskOfNote(mNote.getValue());
        }
        return mTasks;
    }

    public SingleLiveEvent<Task> getCheckBoxTaskEvent() {
        return mCheckBoxTaskEvent;
    }

    public SingleLiveEvent<Task> getDeleteTaskEvent() {
        return mDeleteTaskEvent;
    }

    public void getAllTaskOfNote(Note note) {
        Disposable disposable = mRepository.getAllTaskOfNote(note.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) {
                        mTasks.setValue(tasks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorMessage.setValue(throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void onCheckedChanged(Task task) {
        onTaskEvent(task, ACTION_UPDATE);
    }

    public void onDeleteTaskClicked(Task task) {
        onTaskEvent(task, ACTION_DELETE);
    }

    public void onTaskEvent(Task task, int action) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                switch (action) {
                    case 0:
                        mRepository.updateTask(task);
                        break;
                    default:
                        mRepository.deleteTask(task);
                        break;
                }
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorMessage.setValue(throwable.getMessage());
                    }
                });
    }
}
