package framgia.com.mynote.utils;

import android.os.Environment;

import java.io.File;

public class KeyUtil {
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String FOLDER_APP = "mynote";
    public static final String FOLDER_IMAGES = "images";
    public static final String FOLDER_AUDIOS = "audio";
    public static final String MP3 = ".mp3";
    public static String FOLDER_AUDIOS_PATH =
            PATH.concat(File.separator)
                    .concat("Music")
                    .concat(File.separator)
                    .concat(KeyUtil.FOLDER_APP)
                    .concat(File.separator)
                    .concat(FOLDER_AUDIOS);
}
