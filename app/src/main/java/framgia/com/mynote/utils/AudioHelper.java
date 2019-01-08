package framgia.com.mynote.utils;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class AudioHelper {
    protected static AudioHelper sInstance;
    private MediaRecorder mRecorder;

    private AudioHelper() {
        mRecorder = new MediaRecorder();
    }

    public static AudioHelper getInstance() {
        if (sInstance == null) {
            sInstance = new AudioHelper();
        }
        return sInstance;
    }

    public void init(String path) throws Exception {
        if (!createFile(path)) {
            mRecorder.reset();
            return;
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(path);
    }

    public void start() throws IOException {
        mRecorder.prepare();
        mRecorder.start();
    }

    public void stop() {
        mRecorder.stop();
        mRecorder.reset();
    }

    public void release() {
        mRecorder.release();
    }

    public void createFolder(File file) {
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    private boolean createFile(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.createNewFile();
    }
}
