package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.media.MediaPlayer;

public interface HandlerClick {
    interface AudioHandledClickListener {
        void onPlayAudio();

        void onPlayAudioFailed();

        void onStopAudio();
    }

    interface ImageHandledClickListener {
        void onChooseImageFromGallery();

        void onCreateNewImage();
    }
}
