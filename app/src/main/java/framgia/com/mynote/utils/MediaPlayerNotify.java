package framgia.com.mynote.utils;

import android.content.Context;
import android.media.MediaPlayer;

import framgia.com.mynote.R;

public class MediaPlayerNotify {
    private static MediaPlayer sInstance;

    public static MediaPlayer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = MediaPlayer.create(context, R.raw.alarm);
        }
        return sInstance;
    }
}
