package framgia.com.mynote.data.source.local.note;

import java.util.List;

import framgia.com.mynote.data.NoteDataSource;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import framgia.com.mynote.data.source.local.dao.NoteDAO;
import framgia.com.mynote.data.source.local.dao.TaskDAO;
import io.reactivex.Flowable;

public class NoteLocalDataSource implements NoteDataSource.Local {
    private static NoteLocalDataSource sInstance;
    private NoteDAO mNoteDAO;
    private TaskDAO mTaskDAO;

    public static NoteLocalDataSource getInstance(NoteDAO noteDAO, TaskDAO taskDAO) {
        if (sInstance == null) {
            sInstance = new NoteLocalDataSource(noteDAO, taskDAO);
        }
        return sInstance;
    }

    private NoteLocalDataSource(NoteDAO noteDAO, TaskDAO taskDAO) {
        mNoteDAO = noteDAO;
        mTaskDAO = taskDAO;
    }

    @Override
    public void insertNote(Note note) {
        mNoteDAO.insertNote(note);
    }

    @Override
    public void updateNote(Note note) {
        mNoteDAO.updateNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        mNoteDAO.deleteNote(note);
    }

    @Override
    public void deleteAllNote() {
        mNoteDAO.deleteAllNote();
    }

    @Override
    public Flowable<List<Note>> getAllNote() {
        return mNoteDAO.getAllNote();
    }

    @Override
    public Flowable<List<Note>> getNotesByKey(String key) {
        return mNoteDAO.getNotesByKey(key);
    }

    @Override
    public Flowable<List<Note>> getNotesHasTask(int hasTask) {
        return mNoteDAO.getNotesHasTask(hasTask);
    }

    @Override
    public void insertTask(Task task) {
        mTaskDAO.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mTaskDAO.updateTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mTaskDAO.deleteTask(task);
    }

    @Override
    public void deleteAllTaskOfNote(int nodeId) {
        mTaskDAO.deleteAllTaskOfNote(nodeId);
    }

    @Override
    public Flowable<List<Task>> getAllTaskOfNote(int nodeId) {
        return mTaskDAO.getAllTaskOfNote(nodeId);
    }
}
