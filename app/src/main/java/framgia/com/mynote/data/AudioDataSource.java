package framgia.com.mynote.data;

import java.util.List;

import framgia.com.mynote.data.model.Audio;

public interface AudioDataSource {
    interface Local {
        void getData(Callback<List<Audio>> callback);
    }
}
