package framgia.com.mynote.screen.edit;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;

import framgia.com.mynote.R;

public class Notification {
    private static final String CHANNEL_ID = "my channel";
    private static final String ELLIPSIS = " ...";
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 30;
    private Context mContext;
    private NotificationManagerCompat mManagerCompat;

    public Notification(Context context) {
        mContext = context;
        mManagerCompat = NotificationManagerCompat.from(mContext);
    }

    public void showNotify(int notificationId, NotificationCompat.Builder builder) {
        mManagerCompat.notify(notificationId, builder.build());
    }

    public void cancelNotify(int notificationId) {
        mManagerCompat.cancel(notificationId);
    }

    public NotificationCompat.Builder createNotification(String title, String content) {
        createNotificationChannel();
        return new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_app)
                .setContentTitle(title)
                .setContentText(getSmallContent(content))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private String getSmallContent(String content) {
        if (content.length() > 30) {
            return content.substring(START_INDEX, END_INDEX).concat(ELLIPSIS);
        }
        return content;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.channel_name);
            String description = mContext.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
