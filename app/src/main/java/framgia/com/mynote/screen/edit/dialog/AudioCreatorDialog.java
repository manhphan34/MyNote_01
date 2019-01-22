package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogCreateAudioBinding;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.FileHelper;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Recorder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AudioCreatorDialog extends BaseDialog implements DialogInterface.OnDismissListener {
    public static final String AUDIO_TIME_PATTERN = "mm:ss";
    public static final double DIALOG_WIDTH = 0.5;
    public static final double DIALOG_HEIGHT = 0.2;
    private DialogCreateAudioBinding mBinding;
    private Context mContext;
    private HandlerClick.AudioHandledClickListener mClickListener;
    private boolean mIsRecording = false;
    private String mFile;
    private Recorder mRecorder;
    private Disposable mDisposable;

    public AudioCreatorDialog(Context context, HandlerClick.AudioHandledClickListener handledClickListener) {
        mContext = context;
        mClickListener = handledClickListener;
        mDialogHelper = DialogHelper.getInstance(mContext);
        mRecorder = new Recorder();
    }

    @Override
    public void showDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_create_audio, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        setAutoCancel();
        mBinding.setAudioCreator(this);
        setPostion(Gravity.CENTER);
        mDialogHelper.setDismissDialogListener(this);
        show();
    }

    public void record(String fileName) throws IOException {
        if (fileName == null) {
            fileName = KeyUtils.PATH_FOLDER_AUDIO
                    .concat(File.separator)
                    .concat(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                    .concat(KeyUtils.EXTEND_AUDIO);
        }
        mFile = fileName;
        FileHelper fileHelper = new FileHelper();
        fileHelper.createFile(new File(mFile));
        mRecorder.startRecording(mFile);
    }

    public void onRecording() {
        if (mIsRecording) {
            mIsRecording = false;
            mRecorder.stopRecording();
            mBinding.imageRecord.setImageResource(R.drawable.ic_play);
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            mClickListener.onRecordAudioStop(mFile);
            return;
        }
        updateTime();
        mIsRecording = true;
        mBinding.imageRecord.setImageResource(R.drawable.ic_stop);
        mClickListener.onRecordAudioStart();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dismissDialog();
    }

    private void dismissDialog() {
        if (mIsRecording) {
            mIsRecording = false;
            mRecorder.stopRecording();
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    private void updateTime() {
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mBinding.textTime.setText(DateFormat.format(AUDIO_TIME_PATTERN, new Date(aLong * 1000)).toString());
                });
    }
}
