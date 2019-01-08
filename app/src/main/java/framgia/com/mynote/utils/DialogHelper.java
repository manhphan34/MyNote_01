package framgia.com.mynote.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DialogHelper {
    public static final double DIALOG_AUDIO_WIDTH = 0.7;
    public static final double DIALOG_AUDIO_HEIGHT = 0.15;
    public static final double DIALOG_AUDIO_CREATE_WIDTH = 0.9;
    public static final double DIALOG_AUDIO_CREATE_HEIGHT = 0.3;
    private static DialogHelper sInstance;
    private Dialog mDialog;
    private Activity mActivity;
    private Window mWindow;

    private DialogHelper(Activity activity) {
        mActivity = activity;
        mDialog = new Dialog(activity);
    }

    public static DialogHelper getInstance(Activity activity) {
        if (sInstance == null) {
            sInstance = new DialogHelper(activity);
        }
        return sInstance;
    }

    public void requestWindowFeature() {
        mWindow = mDialog.getWindow();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void initDialog(View view, double w, double h) {
        mDialog.setContentView(view);
        int width = (int) (mActivity.getResources().getDisplayMetrics().widthPixels * w);
        int height = (int) (mActivity.getResources().getDisplayMetrics().heightPixels * h);
        mDialog.getWindow().setLayout(width, height);
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void setLocation(int location) {
        WindowManager.LayoutParams wlp;
        if (mWindow != null) {
            wlp = mWindow.getAttributes();
            wlp.gravity = location;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            mWindow.setAttributes(wlp);
        }
    }
}
