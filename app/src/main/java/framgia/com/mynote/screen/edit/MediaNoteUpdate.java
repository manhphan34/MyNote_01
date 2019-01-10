package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.widget.Toast;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ActivityNoteDetailBinding;
import framgia.com.mynote.utils.Media;

public class MediaNoteUpdate {
    private Note mNote;
    private Media mMedia;
    private HandlerClick.AudioHandleClick mHandleClick;

    public MediaNoteUpdate(Note note, HandlerClick.AudioHandleClick handlerClick) {
        super();
        mHandleClick = handlerClick;
        mNote = note;
        mMedia = Media.getInstance();
    }

    public void playAudio() {
        if (mMedia.isPlaying()) {
            stopRecord();
            changeUIOfAudioImage();
            return;
        }
        play();
    }

    public void onDestroy() {
        mMedia.release();
    }

    private void play() {
        if (mNote != null && mNote.getAudio() != null)
            try {
                mMedia.playAudio(mNote.getAudio());
                changeUIOfAudioImage();
                return;
            } catch (Exception e) {
                mHandleClick.onPlayAudioFailed();
            }
        mHandleClick.onPlayAudioFailed();
    }

    private void stopRecord() {
        mMedia.stop();
        mMedia.reset();
        changeUIOfAudioImage();
    }

    private void changeUIOfAudioImage() {
        if (!mMedia.isPlaying()) {
            mHandleClick.onPlayAudio();
            return;
        }
        mHandleClick.onStopAudio();
    }
}
