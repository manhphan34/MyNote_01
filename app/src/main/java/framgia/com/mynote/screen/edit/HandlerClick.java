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

    interface LocationHandledListener {
        void onGPSTurnOff();

        void onNetWorkTurnOff();

        void onGetLocationSuccess(String location);

        void onGEtLocationFail(Exception e);

        void onLocationEmpty();
    }

    interface DatePickerHandledClickListener {
        void onChooseDate(long time);

        void onChooseDateError(Exception e);
    }

    interface TimePickerHandledClickListener {
        void onChooseHour(long time);

        void onChooseHourError(Exception e);
    }
}
