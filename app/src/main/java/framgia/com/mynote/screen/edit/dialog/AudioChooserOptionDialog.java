package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogAudioOptionBinding;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.FileHelper;
import framgia.com.mynote.utils.KeyUtils;

public class AudioChooserOptionDialog extends BaseDialog {
    public static final double DIALOG_WIDTH = 0.7;
    public static final double DIALOG_HEIGHT = 0.15;
    private DialogAudioOptionBinding mBinding;
    private Context mContext;
    private AudioChooserDialog mAudioChooserDialog;
    private AudioCreatorDialog mAudioCreatorDialog;

    public AudioChooserOptionDialog(Context context, AudioChooserDialog audioChooserDialog,
                                    AudioCreatorDialog audioCreatorDialog) {
        mContext = context;
        mAudioChooserDialog = audioChooserDialog;
        mAudioCreatorDialog = audioCreatorDialog;
        mDialogHelper = DialogHelper.getInstance(mContext);
        FileHelper fileHelper = new FileHelper();
        fileHelper.createFolder(new File(KeyUtils.PATH_FOLDER_AUDIO));
    }

    public void showDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_audio_option, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setAudioOption(this);
        setPostion(Gravity.BOTTOM);
        show();
    }

    public void selectAudioFromStorage() {
        mAudioChooserDialog.showDialog();
    }

    public void onCreateNewAudio() {
        mAudioCreatorDialog.showDialog();
    }
}
