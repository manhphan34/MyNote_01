package framgia.com.mynote.screen.edit.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.screen.edit.Notification;
import framgia.com.mynote.screen.edit.receiver.DismissNotificationReceiver;
import framgia.com.mynote.screen.edit.receiver.OpenDetailReceiver;
import framgia.com.mynote.utils.KeyUtils;
import framgia.com.mynote.utils.Media;
import framgia.com.mynote.utils.MediaPlayerNotify;

public class AlarmService extends Service {
    private Notification mNotification;

    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(KeyUtils.EXTRA_NOTE, note);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaPlayer mediaPlayer = MediaPlayerNotify.getInstance(getApplicationContext());
        mNotification = new Notification(getApplicationContext());
        Note note = intent.getParcelableExtra(KeyUtils.EXTRA_NOTE);
        mNotification.showNotify(note.getId(), getBuilder(note));
        mediaPlayer.start();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private NotificationCompat.Builder getBuilder(Note note) {
        PendingIntent openDetailIntent = PendingIntent.getBroadcast(this, note.getId(),
                OpenDetailReceiver.getIntent(getApplicationContext(), note), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissNotifyIntent = PendingIntent.getBroadcast(this, note.getId(),
                new Intent(this, DismissNotificationReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return mNotification.createNotification(note.getTitle(), note.getDescription())
                .setContentIntent(openDetailIntent)
                .setAutoCancel(true)
                .setDeleteIntent(dismissNotifyIntent);
    }
}
