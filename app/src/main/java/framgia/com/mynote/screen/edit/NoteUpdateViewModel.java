package framgia.com.mynote.screen.edit;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteUpdateViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mId;
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mDescription;
    private MutableLiveData<String> mLocation;
    private MutableLiveData<String> mAudio;
    private MutableLiveData<Long> mTime;
    private MutableLiveData<String> mImageUrl;
    private MutableLiveData<Integer> mHasTask;
    private MutableLiveData<List<Task>> mTasks;
    private MutableLiveData<String> mSuccessMessage;
    private MutableLiveData<String> mErrorMessage;
    private CompositeDisposable mCompositeDisposable;
    private NoteRepository mRepository;
    private TaskAdapter mTaskAdapter;

    public NoteUpdateViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        mRepository = getNoteRepo();
    }

    public MutableLiveData<Integer> getId() {
        if (mId == null) {
            mId = new MutableLiveData<>();
        }
        return mId;
    }

    public MutableLiveData<String> getTitle() {
        if (mTitle == null) {
            mTitle = new MutableLiveData<>();
        }
        return mTitle;
    }

    public MutableLiveData<String> getDescription() {
        if (mDescription == null) {
            mDescription = new MutableLiveData<>();
        }
        return mDescription;
    }

    public MutableLiveData<String> getLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
        }
        return mLocation;
    }

    public MutableLiveData<String> getAudio() {
        if (mAudio == null) {
            mAudio = new MutableLiveData<>();
        }
        return mAudio;
    }

    public MutableLiveData<Long> getTime() {
        if (mTime == null) {
            mTime = new MutableLiveData<>();
        }
        return mTime;
    }

    public MutableLiveData<String> getImageUrl() {
        if (mImageUrl == null) {
            mImageUrl = new MutableLiveData<>();
        }
        return mImageUrl;
    }

    public MutableLiveData<Integer> getHasTask() {
        if (mHasTask == null) {
            mHasTask = new MutableLiveData<>();
        }
        return mHasTask;
    }

    public MutableLiveData<String> getSuccessMessage() {
        if (mSuccessMessage == null) {
            mSuccessMessage = new MutableLiveData<>();
        }
        return mSuccessMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (mErrorMessage == null) {
            mErrorMessage = new MutableLiveData<>();
        }
        return mErrorMessage;
    }

    public MutableLiveData<List<Task>> getTasks() {
        if (mTasks == null) {
            mTasks = new MutableLiveData<>();
        }
        return mTasks;
    }

    public TaskAdapter getTaskAdapter() {
        return mTaskAdapter;
    }

    public void setTaskAdapter(TaskAdapter taskAdapter) {
        mTaskAdapter = taskAdapter;
    }

    private void getData(int nodeId) {
        Disposable disposable = mRepository.getAllTaskOfNote(nodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(tasks -> mTasks.setValue(tasks));
        mCompositeDisposable.add(disposable);
    }

    public void setData(Note note) {
        if (note == null) {
            getIdNote();
            return;
        }
        mId.setValue(note.getId());
        mTitle.setValue(note.getTitle());
        mTime.setValue(note.getTime());
        if (note.getDescription() != null) {
            mDescription.setValue(note.getDescription());
        }
        if (note.getImage() != null) {
            mImageUrl.setValue(note.getImage());
        }
        if (note.getAudio() != null) {
            mAudio.setValue(note.getAudio());
        }
        if (note.getLocation() != null) {
            mLocation.setValue(note.getLocation());
        }
        mHasTask.setValue(note.getHasTask());
        if (mHasTask.getValue() == 1) {
            getData(mId.getValue());
        }
    }

    private NoteRepository getNoteRepo() {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(getApplication());
        return NoteRepository.getInstance(
                NoteLocalDataSource.getInstance(noteDatabase.noteDao(), noteDatabase.taskDAO()));
    }

    private void getIdNote() {
        Disposable disposable = mRepository.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(notes -> {
                    if (notes.size() == 0) {
                        getId().setValue(0);
                        return;
                    }
                    getId().setValue(notes.get(notes.size() - 1).getId() + 1);
                });
        mCompositeDisposable.add(disposable);
    }

    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
