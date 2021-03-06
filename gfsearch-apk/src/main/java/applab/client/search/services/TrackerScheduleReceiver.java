package applab.client.search.services;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by skwakwa on 10/15/15.
 */

public class TrackerScheduleReceiver extends BroadcastReceiver {

    public static final String TAG = TrackerScheduleReceiver.class.getSimpleName();

    // Restart service every 1 hour
    private static final long REPEAT_TIME = 60000;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "running onReceive service");
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, TrackerStartServiceReceiver.class);

        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();

        // Start 30 seconds after boot completed
        cal.add(Calendar.SECOND, 30);
        //
        // every 1 hour
        // InexactRepeating allows Android to optimize the energy consumption
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);
    }
}

