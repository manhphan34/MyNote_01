package framgia.com.mynote.utils;

import android.media.MediaPlayer;

public class Media {
    private MediaPlayer mMediaPlayer;
    private static Media sInstance;

    protected Media() {
        mMediaPlayer = new MediaPlayer();
    }

    public static Media getInstance() {
        if (sInstance == null) {
            sInstance = new Media();
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

    public void playAudio(String path) throws Exception {
        play(path);
    }

    private void play(String path) throws Exception {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            return;
        }
        start(path);
    }

    private void start(String path) throws Exception {
        if (path != null) {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }
}
