package framgia.com.mynote.screen.edit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.screen.edit.service.AlarmService;
import framgia.com.mynote.utils.KeyUtils;

public class AlarmReceiver extends BroadcastReceiver {
    public static Intent getIntent(Context context, Note note) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(KeyUtils.EXTRA_NOTE, note);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Note note = intent.getParcelableExtra(KeyUtils.EXTRA_NOTE);
        context.startService(AlarmService.getIntent(context, note));
    }
}
