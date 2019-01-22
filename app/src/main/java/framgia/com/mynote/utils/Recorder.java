package framgia.com.mynote.utils;

import android.media.MediaRecorder;

import java.io.IOException;

public class Recorder {
    private MediaRecorder mRecorder;

    public Recorder() {
        mRecorder = new MediaRecorder();
    }

    public void startRecording(String fileName) throws IOException {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.prepare();
        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
    }
}
