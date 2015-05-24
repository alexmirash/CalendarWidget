package alex.mirash.mirashcalendarwidget.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;

import static alex.mirash.mirashcalendarwidget.utils.Constants.CLOCK_WIDGET_UPDATE_ACTION;
import static alex.mirash.mirashcalendarwidget.utils.Constants.TIME_UPDATE_INTERVAL;

/**
 * @author Mirash
 */
public final class Utils {
    private static final String LOG_TAG = "LOL";

    public static void log(String msg) {
        Log.d(LOG_TAG, msg);
    }

    public static int getRandomColor() {
        Random r = new Random();
        return Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    public static AlarmManager getAlarmManagerSystemService(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static PendingIntent getClockTickIntent(Context context) {
        return PendingIntent.getBroadcast(
                context, 0, new Intent(CLOCK_WIDGET_UPDATE_ACTION), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelAlarmManagerService(Context context) {
        getAlarmManagerSystemService(context).cancel(getClockTickIntent(context));
    }

    public static void startAlarmManagerService(Context context) {
        AlarmManager alarmManager = getAlarmManagerSystemService(context);
        Calendar calendar = Calendar.getInstance();
        int curMillis = (int) (calendar.getTimeInMillis() % TIME_UPDATE_INTERVAL);
        calendar.add(Calendar.MILLISECOND, (int) (TIME_UPDATE_INTERVAL - curMillis));
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                TIME_UPDATE_INTERVAL, getClockTickIntent(context));
    }

    public static int getDayOfWeekColor(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == 1 || day == 6 ? Constants.DAY_TEXT_COLOR_WEEKEND : Constants.DAY_TEXT_COLOR_DEFAULT;
    }

}
