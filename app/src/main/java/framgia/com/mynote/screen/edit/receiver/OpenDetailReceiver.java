package framgia.com.mynote.screen.edit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.screen.detail_note.DetailNoteActivity;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Media;
import framgia.com.mynote.utils.MediaPlayerNotify;

public class OpenDetailReceiver extends BroadcastReceiver {
    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, OpenDetailReceiver.class);
        intent.putExtra(KeyUtils.EXTRA_NOTE, note);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayerNotify.getInstance(context);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.prepareAsync();
        }
        Intent detailIntent = DetailNoteActivity.getDetailNoteIntent(context, intent.getParcelableExtra(KeyUtils.EXTRA_NOTE));
        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(detailIntent);
    }
}
