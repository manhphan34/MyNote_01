package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Audio;
import framgia.com.mynote.data.repository.AudioRepository;
import framgia.com.mynote.data.source.local.audio.AudioLocalDataSource;
import framgia.com.mynote.databinding.DialogAudioChooseBinding;
import framgia.com.mynote.screen.edit.AudioAdapter;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DialogHelper;

public class AudioChooserDialog extends BaseDialog implements
        HandlerClick.ItemAudioClickListener {
    private AudioAdapter mAudioAdapter;
    public static final double DIALOG_WIDTH = 0.9;
    public static final double DIALOG_HEIGHT = 0.9;
    private DialogAudioChooseBinding mBinding;
    private HandlerClick.AudioHandledClickListener mClickListener;
    private Context mContext;

    public AudioChooserDialog(Context context,
                              HandlerClick.AudioHandledClickListener handledClickListener) {
        mContext = context;
        mClickListener = handledClickListener;
        mDialogHelper = DialogHelper.getInstance(mContext);
    }

    @Override
    public void onAudioItemSelected(Audio audio) {
        mClickListener.onSelectedAudio(audio);
    }

    @Override
    public void setLocation() {

    }

    public void showDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_audio_choose, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setAudio(this);
        setPostion(Gravity.CENTER);
        showData();
        show();
    }

    public void getData() {
        getRepo().getData(data -> mAudioAdapter.replaceData(data));
    }

    public void showData() {
        mAudioAdapter = new AudioAdapter(mContext, this);
        mBinding.recycleAudio.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recycleAudio.addItemDecoration(new DividerItemDecoration(mContext,
                LinearLayoutManager.VERTICAL));
        mBinding.recycleAudio.setItemAnimator(new DefaultItemAnimator());
        getData();
    }

    public AudioAdapter getAudioAdapter() {
        return mAudioAdapter;
    }

    private AudioRepository getRepo() {
        return AudioRepository.getInstance(
                AudioLocalDataSource.getInstance(mContext.getContentResolver()));
    }
}
