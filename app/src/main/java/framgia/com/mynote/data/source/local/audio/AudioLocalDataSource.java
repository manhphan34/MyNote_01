package framgia.com.mynote.data.source.local.audio;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.data.AudioDataSource;
import framgia.com.mynote.data.Callback;
import framgia.com.mynote.data.model.Audio;

public class AudioLocalDataSource implements AudioDataSource.Local {
    private ContentResolver mContentResolver;
    private static AudioLocalDataSource sInstance;

    private AudioLocalDataSource(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public static AudioLocalDataSource getInstance(ContentResolver contentResolver) {
        if (sInstance == null) {
            sInstance = new AudioLocalDataSource(contentResolver);
        }
        return sInstance;
    }

    @Override
    public void getData(Callback<List<Audio>> callback) {
        List<Audio> audios = new ArrayList<>();
        Cursor audioCursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);

        if (audioCursor == null) {
            return;
        }
        while (audioCursor.moveToNext()) {
            String name = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            String path = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            audios.add(new Audio(name, path));
        }
        callback.onGetData(audios);
        audioCursor.close();
    }
}
