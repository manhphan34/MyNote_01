package framgia.com.mynote.utils;

import android.os.Environment;

import java.io.File;

public class KeyUtils {
    public static String EXTRA_NOTE = "EXTRA_NOTE";
    public static final String FOLDER_APP = "mynote";
    public static final String FOLDER_IMAGE = "image";
    public static final String PATH_FOLDER_IMAGE =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    .concat(File.separator)
                    .concat(FOLDER_APP)
                    .concat(File.separator)
                    .concat(FOLDER_IMAGE);
    public static final String EXTEND_IMAGE = ".png";
    public static final String FOLDER_AUDIO = "audio";
    public static final String PATH_FOLDER_AUDIO =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    .concat(File.separator)
                    .concat(FOLDER_APP)
                    .concat(File.separator)
                    .concat(FOLDER_AUDIO);
    public static final String EXTEND_AUDIO = ".mp3";
}
