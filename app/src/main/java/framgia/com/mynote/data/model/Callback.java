package framgia.com.mynote.data.model;

import java.util.List;

public interface Callback {
    void onGetDataSuccess(List<Note> notes);

    void onGetDataFailed(Exception e);
}
