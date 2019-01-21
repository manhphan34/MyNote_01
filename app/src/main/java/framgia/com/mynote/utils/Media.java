package framgia.com.mynote.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import framgia.com.mynote.R;

public class Media {
    private MediaPlayer mMediaPlayer;
    private static Media sInstance;
    private Context mContext;

    protected Media(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    public static Media getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Media(context);
        }
        return sInstance;
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void reset() {
        mMediaPlayer.reset();
    }

    public void release() {
        mMediaPlayer.release();
    }

    public void playAudio(String path) throws IOException {
        play(path);
    }

    public void playMediaFromSource() {
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.alarm);
        mMediaPlayer.start();
    }

    private void play(String path) throws IOException {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            return;
        }
        start(path);
    }

    private void start(String path) throws IOException {
        if (path != null) {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }

    public void setOnComplete(MediaPlayer.OnCompletionListener onComplete) {
        mMediaPlayer.setOnCompletionListener(onComplete);
    }
}
