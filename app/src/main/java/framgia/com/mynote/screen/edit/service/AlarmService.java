package framgia.com.mynote.screen.edit.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.screen.edit.Notification;
import framgia.com.mynote.screen.edit.receiver.DismissNotificationReceiver;
import framgia.com.mynote.screen.edit.receiver.OpenDetailReceiver;
import framgia.com.mynote.utils.Media;

public class AlarmService extends Service {
    private Media mMedia;
    private Notification mNotification;
    public static final String NOTE_EXTRA = "NOTE_EXTRA";

    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(NOTE_EXTRA, note);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMedia = Media.getInstance(getApplicationContext());
        mNotification = new Notification(getApplicationContext());
        Note note = intent.getParcelableExtra(NOTE_EXTRA);
        mNotification.showNotify(note.getId(), getBuilder(note));
        mMedia.playMediaFromSource();
        return super.onStartCommand(intent, flags, startId);
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
