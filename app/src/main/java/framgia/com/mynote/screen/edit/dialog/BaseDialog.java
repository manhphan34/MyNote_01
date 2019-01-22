package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import framgia.com.mynote.R;
import framgia.com.mynote.utils.DialogHelper;

public abstract class BaseDialog {
    protected DialogHelper mDialogHelper;
    protected Context mContext;

    public abstract void setLocation();

    public abstract void showDialog();

    public void init(double w, double h) {
        mDialogHelper.initDialog(w, h);
    }

    public void show() {
        mDialogHelper.show();
    }

    public void setAutoCancel() {
        mDialogHelper.setAutoCancel();
    }

    public void dismiss() {
        mDialogHelper.dismiss();
    }

    public void setPostion(int position) {
        Window window = mDialogHelper.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.gravity = position;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        }
    }
}
