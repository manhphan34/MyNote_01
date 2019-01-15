package framgia.com.mynote.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {
    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 0xc8;
    public static final int REQUEST_RECORD_IMAGE_PERMISSION = 0xc9;
    public static final int REQUEST_LOCATION_PERMISSION = 0xca;
    private Activity mActivity;
    private static final String[] RECORD_AUDIO_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private static final String[] RECORD_IMAGE_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
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

    public boolean requestPermissionImage() {
        if (isCheckSelfPermission(RECORD_IMAGE_PERMISSION)) {
            requestPermission(RECORD_IMAGE_PERMISSION, REQUEST_RECORD_IMAGE_PERMISSION);
            return true;
        }
        return false;
    }

    public boolean requestPermissionLocation() {
        if (isCheckSelfPermission(LOCATION_PERMISSION)) {
            requestPermission(LOCATION_PERMISSION, REQUEST_LOCATION_PERMISSION);
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
