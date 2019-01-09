package framgia.com.mynote.screen.edit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.data.repository.NoteRepository;
import framgia.com.mynote.data.source.local.note.NoteDatabase;
import framgia.com.mynote.data.source.local.note.NoteLocalDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TaskViewModel extends AndroidViewModel {
    private MutableLiveData<List<Task>> mTasks;
    private CompositeDisposable mDisposable;
    private NoteRepository mNoteRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = getNoteRepo();
        mDisposable = new CompositeDisposable();
    }

    public MutableLiveData<List<Task>> getTasks(int nodeId) {
        if (mTasks == null) {
            mTasks = new MutableLiveData<>();
            getData(nodeId);
        }
        return mTasks;
    }

    private void getData(int nodeId) {
        Disposable disposable = mNoteRepository.getAllTaskOfNote(nodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        mTasks.setValue(tasks);
                    }
                });
        mDisposable.add(disposable);
    }

    public void onDetach() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private NoteRepository getNoteRepo() {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(getApplication());
        return NoteRepository.getInstance(
                NoteLocalDataSource.getInstance(noteDatabase.noteDao(), noteDatabase.taskDAO()));
    }
}
