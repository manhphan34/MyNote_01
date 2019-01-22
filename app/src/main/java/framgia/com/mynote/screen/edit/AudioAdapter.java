package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Audio;
import framgia.com.mynote.databinding.ItemAudioBinding;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private Context mContext;
    private List<Audio> mAudios;
    private HandlerClick.ItemAudioClickListener mClickListener;

    public AudioAdapter(Context context, HandlerClick.ItemAudioClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
        mAudios = new ArrayList<>();
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAudioBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_audio, viewGroup, false);
        return new AudioViewHolder(binding, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder audioViewHolder, int i) {
        audioViewHolder.onBind(mAudios.get(i));
    }

    public void replaceData(List<Audio> audios) {
        if (audios == null) {
            return;
        }
        if (mAudios.size() > 0) {
            mAudios.removeAll(mAudios);
        }
        mAudios.addAll(audios);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAudios == null ? 0 : mAudios.size();
    }


    public static class AudioViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ItemAudioBinding mBinding;
        private Audio mAudio;
        private HandlerClick.ItemAudioClickListener mListener;

        public AudioViewHolder(@NonNull ItemAudioBinding binding,
                               HandlerClick.ItemAudioClickListener handledClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = handledClickListener;
            mBinding.constrainAudio.setOnClickListener(this);

        }

        public void onBind(Audio audio) {
            mAudio = audio;
            mBinding.setAudio(audio);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            mListener.onAudioItemSelected(mAudio);
        }
    }
}
