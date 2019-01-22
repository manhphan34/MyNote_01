package framgia.com.mynote.data;

import java.util.List;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.data.model.Task;
import io.reactivex.Flowable;

public interface NoteDataSource {
    interface Local {
        void insertNote(Note note);

        void updateNote(Note note);

        void deleteNote(Note note);

        void deleteAllNote();

        Flowable<List<Note>> getNotes();

        Flowable<List<Note>> getNotesByKey(String key);

        Flowable<List<Note>> getNotesHasTask(int hasTask);

        void insertTask(Task task);

        void insertTasks(List<Task> tasks);

        void updateTask(Task task);

        void updateTask(List<Task> tasks);

        void deleteTask(Task task);

        void deleteAllTaskOfNote(int nodeId);

        Flowable<List<Task>> getAllTaskOfNote(int nodeId);
    }
}
