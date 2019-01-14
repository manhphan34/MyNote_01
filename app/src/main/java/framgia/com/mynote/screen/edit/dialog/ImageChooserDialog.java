package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogChooseOptionImageBinding;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.FileHelper;
import framgia.com.mynote.utils.KeyUtils;

public class ImageChooserDialog extends BaseDialog {
    public static final double DIALOG_WIDTH = 0.7;
    public static final double DIALOG_HEIGHT = 0.15;
    private HandlerClick.ImageHandledClickListener mImageHandledClickListener;
    private DialogChooseOptionImageBinding mBinding;
    private Context mContext;

    public ImageChooserDialog(Context context, HandlerClick.ImageHandledClickListener imageHandledClickListener) {
        mContext = context;
        mDialogHelper = DialogHelper.getInstance(mContext);
        mImageHandledClickListener = imageHandledClickListener;
        FileHelper fileHelper = new FileHelper();
        fileHelper.createFolder(new File(KeyUtils.PATH_FOLDER_IMAGE));
    }

    @Override
    public void setLocation() {
        Window window = mDialogHelper.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM | Gravity.LEFT;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        }
    }

    public void showDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_choose_option_image, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setImageOption(this);
        setLocation();
        show();
    }

    public void createNewImage() {
        if (mImageHandledClickListener == null) return;
        mImageHandledClickListener.onCreateNewImage();
    }

    public void chooseImageFromGallery() {
        if (mImageHandledClickListener == null) return;
        mImageHandledClickListener.onChooseImageFromGallery();
    }
}
