package framgia.com.mynote.screen.edit;

import framgia.com.mynote.data.model.Audio;
import framgia.com.mynote.data.model.Task;

public interface HandlerClick {
    interface AudioHandledClickListener {
        void onPlayAudio();

        void onPlayAudioFailed();

        void onStopAudio();

        void onSelectedAudio(Audio audio);

        void onRecordAudioStop(String filePath);

        void onRecordAudioStart();

        void onRecordAudioFail();

        void onRecorded(String path);
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

    interface ItemAudioClickListener {
        void onAudioItemSelected(Audio audio);
    }

    interface TaskHandleListener {
        void onStatusChange(int pos, Task task);

        void onDeleteTask(int pos);

        void onTitleChange(int pos, Task task);
    }
}
