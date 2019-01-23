package framgia.com.mynote.data.repository;

import java.util.List;

import framgia.com.mynote.data.AudioDataSource;
import framgia.com.mynote.data.Callback;
import framgia.com.mynote.data.model.Audio;
import framgia.com.mynote.data.source.local.audio.AudioLocalDataSource;

public class AudioRepository implements AudioDataSource.Local {
    private AudioDataSource.Local mLocal;
    private static AudioRepository sInstance;

    private AudioRepository(AudioDataSource.Local local) {
        mLocal = local;
    }

    public static AudioRepository getInstance(AudioDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new AudioRepository(local);
        }
        return sInstance;
    }

    @Override
    public void getData(Callback<List<Audio>> callback) {
        mLocal.getData(callback);
    }
}
