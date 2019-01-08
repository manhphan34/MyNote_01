package framgia.com.mynote.screen.edit;

public interface HandlerClick {
    interface AudioHandleClick {
        void onPlayAudio();

        void onPlayAudioFailed();

        void onStopAudio();
    }
}
