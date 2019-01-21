package framgia.com.mynote.screen.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import io.reactivex.disposables.CompositeDisposable;

public class NoteUpdateViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mId;
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mDescription;
    private MutableLiveData<String> mLocation;
    private MutableLiveData<String> mAudio;
    private MutableLiveData<Long> mTime;
    private MutableLiveData<String> mImage;
    private MutableLiveData<Integer> mHasTask;
    private MutableLiveData<String> mSuccessMessage;
    private MutableLiveData<String> mErrorMessage;
    private CompositeDisposable mCompositeDisposable;
    private NoteRepository mRepository;

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

    public MutableLiveData<String> getImage() {
        if (mImage == null) {
            mImage = new MutableLiveData<>();
        }
        return mImage;
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

    public void setData(Note note) {
        if (note == null) {
            return;
        }
        mId.setValue(note.getId());
        mTitle.setValue(note.getTitle());
        mTime.setValue(note.getTime());
        if (note.getDescription() != null) {
            mDescription.setValue(note.getDescription());
        }
        if (note.getImage() != null) {
            mImage.setValue(note.getImage());
        }
        if (note.getAudio() != null) {
            mAudio.setValue(note.getAudio());
        }
        if (note.getLocation() != null) {
            mLocation.setValue(note.getLocation());
        }
        mHasTask.setValue(note.getHasTask());
    }

    private NoteRepository getNoteRepo() {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(getApplication());
        return NoteRepository.getInstance(
                NoteLocalDataSource.getInstance(noteDatabase.noteDao(), noteDatabase.taskDAO()));
    }

    private Note initNote() {
        Note.Builder builder = new Note.Builder();
        builder.setId(mId.getValue())
                .setTitle(mTitle.getValue())
                .setDescription(mDescription.getValue())
                .setDescription(mAudio.getValue())
                .setImage(mImage.getValue())
                .setLocation(mLocation.getValue())
                .setTime(mTime.getValue());
    }
}
