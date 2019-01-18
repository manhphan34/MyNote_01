package framgia.com.mynote.screen.edit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import framgia.com.mynote.utils.Media;

public class DismissNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Media media = Media.getInstance(context);
        if (media.isPlaying()) {
            media.stop();
            media.release();
        }
    }
}
