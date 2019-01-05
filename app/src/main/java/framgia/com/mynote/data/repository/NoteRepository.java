package framgia.com.mynote.data.repository;

import java.util.List;

import framgia.com.mynote.data.NoteDataSource;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import io.reactivex.Flowable;

public class NoteRepository implements NoteDataSource.Local {
    private static NoteRepository sInstance;
    private NoteDataSource.Local mLocal;

    public static NoteRepository getInstance(NoteDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new NoteRepository(local);
        }
        return sInstance;
    }

    private NoteRepository(NoteDataSource.Local local) {
        mLocal = local;
    }

    @Override
    public void insertNote(Note note) {
        mLocal.insertNote(note);
    }

    @Override
    public void updateNote(Note note) {
        mLocal.updateNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        mLocal.deleteNote(note);
    }

    @Override
    public void deleteAllNote() {
        mLocal.deleteAllNote();
    }

    @Override
    public Flowable<List<Note>> getNotes() {
        return mLocal.getNotes();
    }

    @Override
    public Flowable<List<Note>> getNotesByKey(String key) {
        return mLocal.getNotesByKey(key);
    }

    @Override
    public Flowable<List<Note>> getNotesHasTask(int hasTask) {
        return mLocal.getNotesHasTask(hasTask);
    }

    @Override
    public void insertTask(Task task) {
        mLocal.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mLocal.updateTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mLocal.deleteTask(task);
    }

    @Override
    public void deleteAllTaskOfNote(int nodeId) {
        mLocal.deleteAllTaskOfNote(nodeId);
    }

    @Override
    public Flowable<List<Task>> getAllTaskOfNote(int nodeId) {
        return mLocal.getAllTaskOfNote(nodeId);
    }
}
