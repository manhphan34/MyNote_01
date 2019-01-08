package framgia.com.mynote.screen.edit;

import java.io.File;

import framgia.com.mynote.utils.AudioHelper;
import framgia.com.mynote.utils.KeyUtil;

public class AudioNoteUpdate {
    private AudioHelper mAudioHelper;

    public AudioNoteUpdate() {
        mAudioHelper = AudioHelper.getInstance();
        createFolder();
    }

    public void record(){
        mAudioHelper.init(createPath());
    }
    private void createFolder() {
        mAudioHelper.createFolder(new File(KeyUtil.FOLDER_AUDIOS_PATH));
    }

    private String createPath(String fileName) {
        return KeyUtil.FOLDER_AUDIOS_PATH.
                concat(File.separator)
                .concat(fileName)
                .concat(KeyUtil.MP3);
    }
}
