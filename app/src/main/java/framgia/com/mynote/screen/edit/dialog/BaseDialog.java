package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.text.Layout;

import framgia.com.mynote.R;
import framgia.com.mynote.utils.DialogHelper;

public abstract class BaseDialog {
    protected DialogHelper mDialogHelper;

    public abstract void setLocation();

    public void init(double w, double h) {
        mDialogHelper.initDialog(w, h);
    }

    public void show() {
        mDialogHelper.show();
    }

    public void dissmiss() {
        mDialogHelper.dismiss();
    }
}
