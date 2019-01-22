package framgia.com.mynote.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import framgia.com.mynote.R;

import static android.content.DialogInterface.OnClickListener;

public class DialogHelper {
    private static DialogHelper sInstance;
    private Dialog mDialog;
    private Context mContext;
    private Window mWindow;

    public static void release() {
        if (sInstance == null) {
            return;
        }
        sInstance = null;
    }

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

    public static void showConfirmDeleteDialog(Context context, String title, String message,
                                               OnClickListener buttonPositiveLisener,
                                               OnClickListener buttonNagativeLisener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.button_positive, buttonPositiveLisener);
        builder.setNegativeButton(R.string.button_negative, buttonNagativeLisener);
        builder.show();
    }

    public void setAutoCancel() {
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void setDismissDialogListener(DialogInterface.OnDismissListener dismissDialogListener) {
        mDialog.setOnDismissListener(dismissDialogListener);
    }
}
