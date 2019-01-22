package framgia.com.mynote.screen.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import io.reactivex.Observable;
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
    private MutableLiveData<String> mImage;
    private MutableLiveData<Integer> mHasTask;
    private MutableLiveData<List<Task>> mTasks;
    private MutableLiveData<String> mSuccessMessage;
    private MutableLiveData<String> mErrorMessage;
    private MutableLiveData<String> mSaveNoteSuccess;
    private CompositeDisposable mCompositeDisposable;
    private NoteRepository mRepository;
    private TaskAdapter mTaskAdapter;
    private List<Task> mTasksCopy;

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

    public MutableLiveData<List<Task>> getTasks() {
        if (mTasks == null) {
            mTasks = new MutableLiveData<>();
        }
        return mTasks;
    }

    public MutableLiveData<String> getSaveNoteSuccess() {
        if (mSaveNoteSuccess == null) {
            mSaveNoteSuccess = new MutableLiveData<>();
        }
        return mSaveNoteSuccess;
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
                .subscribe(tasks -> {
                    mTasks.setValue(tasks);
                });
        mCompositeDisposable.add(disposable);
    }

    public void setData(Note note) {
        if (note == null) {
            getIdNote();
            return;
        }
        getId().setValue(note.getId());
        getTitle().setValue(note.getTitle());
        getTime().setValue(note.getTime());
        getDescription().setValue(note.getDescription());
        getImage().setValue(note.getImage());
        getTime().setValue(note.getTime());
        getAudio().setValue(note.getAudio());
        getLocation().setValue(note.getLocation());
        getHasTask().setValue(note.getHasTask());
        if (getHasTask().getValue() == 0) {
            return;
        }
        getData(mId.getValue());
    }

    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void insert() {
        if (mTasks.getValue() != null && mTasks.getValue().size() > 0) {
            insertNoteWithTask();
            return;
        }
        insertNoteWithoutTask();
    }

    public void onTitleChange(CharSequence s, int start, int before, int count) {
        mTitle.setValue(s.toString());
    }

    public void onDescriptionChange(CharSequence s, int start, int before, int count) {
        mDescription.setValue(s.toString());
    }

    public void update() {
        if (mTasks.getValue() != null && mTasks.getValue().size() > 0) {
            updateNoteWithTask();
            return;
        }
        updateNoteWithoutTask();
    }

    public Note getNote() {
        return initNote();
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

    private Note initNote() {
        Note.Builder builder = new Note.Builder();
        builder.setId(mId.getValue());
        builder.setTitle(mTitle.getValue());
        if (mDescription.getValue() != null && !mDescription.getValue().isEmpty()) {
            builder.setDescription(mDescription.getValue());
        }
        if (mTime.getValue() != null) {
            builder.setTime(mTime.getValue() == null ? 0 : mTime.getValue());
        }
        if (mAudio.getValue() != null && !mAudio.getValue().isEmpty()) {
            builder.setAudio(mAudio.getValue());
        }
        if (mImage.getValue() != null && !mImage.getValue().isEmpty()) {
            builder.setImage(mImage.getValue());
        }
        if (mLocation.getValue() != null && !mLocation.getValue().isEmpty()) {
            builder.setLocation(mLocation.getValue());
        }
        if (getTasks().getValue() != null) {
            builder.setTask(mTasks.getValue().size() == 0 ? 0 : 1);
        }
        return builder.build();
    }

    private Observable<Boolean> insertNote() {
        if (mTitle.getValue() == null || getTitle().getValue().isEmpty()) {
            mErrorMessage.setValue(getApplication().getString(R.string.note_title_is_empty));
            return null;
        }
        Note note = initNote();
        Observable<Boolean> insertNote = Observable.create(emitter -> {
            mRepository.insertNote(note);
            emitter.onNext(true);
            emitter.onComplete();
        });
        return insertNote;
    }

    private void insertNoteWithoutTask() {
        Observable<Boolean> saveNote = insertNote();
        if (saveNote == null) {
            return;
        }
        saveNote.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> mSaveNoteSuccess.setValue(getApplication().getString(R.string.message_save_note_success)),
                        throwable -> mErrorMessage.setValue(throwable.getMessage()));
    }

    private void insertNoteWithTask() {
        Observable<Boolean> saveNote = insertNote();
        if (saveNote == null) {
            return;
        }
        insertTask(saveNote);
    }

    private void insertTask(Observable<Boolean> saveNote) {
        Observable<Boolean> saveTask = Observable.create(emitter -> {
            mRepository.insertTasks(mTasks.getValue());
            emitter.onNext(true);
        });
        Observable.merge(saveNote, saveTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> mSaveNoteSuccess.setValue(getApplication().getString(R.string.message_save_note_success)),
                        throwable -> mErrorMessage.setValue(throwable.getMessage()));
    }

    private Observable<Boolean> updateNote() {
        if (mTitle.getValue() == null || getTitle().getValue().isEmpty()) {
            mErrorMessage.setValue(getApplication().getString(R.string.note_title_is_empty));
            return null;
        }
        Note note = initNote();
        return Observable.create(emitter -> {
            mRepository.updateNote(note);
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    private void updateNoteWithoutTask() {
        Observable<Boolean> updateNote = updateNote();
        if (updateNote == null) {
            return;
        }
        updateNote.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> mSaveNoteSuccess.setValue(getApplication().getString(R.string.message_save_note_success)),
                        throwable -> mErrorMessage.setValue(throwable.getMessage()));
    }

    private void updateNoteWithTask() {
        Observable<Boolean> updateNote = updateNote();
        if (updateNote == null) {
            return;
        }
        Observable<Boolean> deleteTask = deleteTasks();
        updateTask(updateNote, deleteTask);
    }

    private Observable<Boolean> deleteTasks() {
        mTasksCopy = mTasks.getValue();
        return Observable.create(emitter -> {
            mRepository.deleteAllTaskOfNote(mId.getValue());
            emitter.onNext(true);
        });
    }

    private void insertTask(Observable<Boolean> deleteTasks, Observable<Boolean> saveNote) {
        Observable<Boolean> saveTask = Observable.create(emitter -> {
            mRepository.insertTasks(mTasksCopy);
            emitter.onNext(true);
        });
        Observable.merge(saveNote, deleteTasks, saveTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> mSaveNoteSuccess.setValue(getApplication().getString(R.string.message_save_note_success)),
                        throwable -> mErrorMessage.setValue(throwable.getMessage()));
    }

    private void updateTask(Observable<Boolean> saveNote, Observable<Boolean> deleteTaks) {
        insertTask(saveNote, deleteTaks);
    }
}
