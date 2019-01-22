package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.utils.Media;

public class MediaNoteUpdate implements MediaPlayer.OnCompletionListener {
    private Media mMedia;
    private HandlerClick.AudioHandledClickListener mHandleClick;

    public MediaNoteUpdate(Context context, HandlerClick.AudioHandledClickListener handlerClick) {
        super();
        mHandleClick = handlerClick;
        mMedia = Media.getInstance(context);
        mMedia.setOnComplete(this::onCompletion);
    }

    public void playAudio(String path) {
        if (mMedia.isPlaying()) {
            stop();
            changeUIOfAudioImage();
            return;
        }
        play(path);
    }

    public boolean isPlaying() {
        return mMedia.isPlaying();
    }

    public void onDestroy() {
        mMedia.release();
    }

    private void play(String path) {
        try {
            mMedia.playAudio(path);
            changeUIOfAudioImage();
        } catch (IOException e) {
            mHandleClick.onPlayAudioFailed();
        }
    }

    public void stop() {
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

    @Override
    public void onCompletion(MediaPlayer mp) {
        mHandleClick.onPlayAudio();
        mMedia.reset();
    }
}
