package framgia.com.mynote.screen.edit;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.media.MediaPlayer;

public interface HandlerClick {
    interface AudioHandleClick {
        void onPlayAudio();

        void onPlayAudioFailed();

        void onStopAudio();
    }
}
