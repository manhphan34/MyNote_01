package framgia.com.mynote.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import framgia.com.mynote.R;

public class DialogHelper {
    private static DialogHelper sInstance;
    private Dialog mDialog;
    private Context mContext;
    private Window mWindow;

    private DialogHelper(Context context) {
        mContext = context;
        mDialog = new Dialog(mContext);
        requestWindowFeature();
    }

    public static DialogHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DialogHelper(context);
        }
        return sInstance;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void requestWindowFeature() {
        mWindow = mDialog.getWindow();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void initDialog(double w, double h) {
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * w);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * h);
        mDialog.getWindow().setLayout(width, height);
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public Activity getActivity() {
        return mDialog.getOwnerActivity();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void setView(View view) {
        mDialog.setContentView(view);
    }
}
