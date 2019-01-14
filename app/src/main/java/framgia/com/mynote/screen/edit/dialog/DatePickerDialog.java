package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogDatePickerBinding;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DateTimeUtil;
import framgia.com.mynote.utils.DialogHelper;

public class DatePickerDialog extends BaseDialog {
    public static final double DIALOG_WIDTH = 0.9;
    public static final double DIALOG_HEIGHT = 0.7;
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    private HandlerClick.DatePickerHandledClickListener mHandleClick;
    private DialogDatePickerBinding mBinding;

    public DatePickerDialog(Context context, HandlerClick.DatePickerHandledClickListener handleClick) {
        mContext = context;
        mHandleClick = handleClick;
        mDialogHelper = DialogHelper.getInstance(context);
    }

    @Override
    public void setLocation() {
        Window window = mDialogHelper.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        }
    }

    @Override
    public void showDialog() {
        if (mDialogHelper.isShowing()) {
            mDialogHelper.dismiss();
        }
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_date_picker, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setDate(this);
        setLocation();
        show();
    }

    public void onCancel() {
        mDialogHelper.dismiss();
    }

    public void onSubmit() {
        mHandleClick.onChooseDate(getTime(mBinding.datePicker));
    }

    private long getTime(DatePicker datePicker) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        try {
            return sdf.parse(getDate(datePicker.getYear(),
                    datePicker.getMonth() + 1,
                    datePicker.getDayOfMonth())).getTime();
        } catch (ParseException e) {
            mHandleClick.onChooseDateError(e);
        }
        return 0L;
    }

    private String getDate(int year, int month, int day) {
        return String.valueOf(day)
                .concat(File.separator)
                .concat(String.valueOf(month))
                .concat(File.separator)
                .concat(String.valueOf(year));
    }
}
