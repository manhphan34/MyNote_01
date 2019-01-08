package framgia.com.mynote.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {
    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 0xc8;
    private Activity mActivity;
    private static final String[] RECORD_AUDIO_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public Permission(Activity activity) {
        mActivity = activity;
    }

    public boolean requestPermissionAudio() {
        if (isCheckSelfPermission(RECORD_AUDIO_PERMISSION)) {
            requestPermission(RECORD_AUDIO_PERMISSION, REQUEST_RECORD_AUDIO_PERMISSION);
            return true;
        }
        return false;
    }

    private boolean isCheckSelfPermission(String... permissions) {
        if (mActivity != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mActivity,
                        permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(mActivity, permissions,
                requestCode);
    }
}