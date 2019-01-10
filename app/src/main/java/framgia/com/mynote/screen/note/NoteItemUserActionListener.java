package framgia.com.mynote.screen.note;

import framgia.com.mynote.data.model.Note;

public interface NoteItemUserActionListener {
    void onNoteClicked(Note note);
    void onEditClicked(Note note);
    void onDeleteClicked(Note note);
    void onAddToWidgetClicked(Note note);
}
