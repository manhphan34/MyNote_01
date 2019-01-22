package framgia.com.mynote.screen.edit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

import framgia.com.mynote.R;
import framgia.com.mynote.utils.Media;
import framgia.com.mynote.utils.MediaPlayerNotify;

public class DismissNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayerNotify.getInstance(context);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.prepareAsync();
        }
    }
}
