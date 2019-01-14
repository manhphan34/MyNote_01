package framgia.com.mynote.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

    public boolean createFolder(File folder) {
        if (folder.exists()) {
            return true;
        }
        return folder.mkdirs();
    }

    public boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return true;
        }
        return file.createNewFile();
    }

    public void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public void writeFileImage(File file, Bitmap bitmap) throws IOException {
        FileOutputStream fOut = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
    }
}
