package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogTimePickerBinding;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DateTimeUtil;
import framgia.com.mynote.utils.DialogHelper;

public class TimePickerDialog extends BaseDialog {
    public static final double DIALOG_WIDTH = 0.9;
    public static final double DIALOG_HEIGHT = 0.7;
    private static final String COLON = ":";
    private HandlerClick.TimePickerHandledClickListener mHandleClick;
    private DialogTimePickerBinding mBinding;
    private long mTime;
    private static final String FORMAT_TIME = "hh:mm";

    public TimePickerDialog(Context context,
                            HandlerClick.TimePickerHandledClickListener handleClick) {
        mContext = context;
        mDialogHelper = DialogHelper.getInstance(mContext);
        mHandleClick = handleClick;
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
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_time_picker, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setTimePicker(this);
        setLocation();
        show();
    }

    public void onCancel() {
        mDialogHelper.dismiss();
    }

    public void onSubmit() {
        mHandleClick.onChooseHour(getTime(mBinding.timePicker));
    }

    private Long getTime(TimePicker timePicker) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        try {
            return sdf.parse(getDate(timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute())).getTime();
        } catch (ParseException e) {
            mHandleClick.onChooseHourError(e);
        }
        return 0L;
    }

    private String getDate(int hour, int minute) {
        return String.valueOf(hour)
                .concat(COLON)
                .concat(String.valueOf(minute));
    }
}
