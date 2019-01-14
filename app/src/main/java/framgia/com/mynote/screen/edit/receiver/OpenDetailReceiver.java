package framgia.com.mynote.screen.edit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.screen.detail_note.DetailNoteActivity;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Media;

public class OpenDetailReceiver extends BroadcastReceiver {
    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, OpenDetailReceiver.class);
        intent.putExtra(KeyUtils.EXTRA_NOTE, note);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Media media = Media.getInstance(context);
        if (media.isPlaying()) {
            media.stop();
        }
        Intent detailIntent = DetailNoteActivity.getDetailNoteIntent(context, intent.getParcelableExtra(KeyUtils.EXTRA_NOTE));
        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
