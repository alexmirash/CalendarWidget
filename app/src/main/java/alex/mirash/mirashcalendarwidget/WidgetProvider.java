package alex.mirash.mirashcalendarwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;

import java.util.Calendar;

import alex.mirash.mirashcalendarwidget.utils.Constants;
import alex.mirash.mirashcalendarwidget.utils.Utils;

import static alex.mirash.mirashcalendarwidget.utils.Constants.DATE_FORMAT;
import static alex.mirash.mirashcalendarwidget.utils.Utils.cancelAlarmManagerService;
import static alex.mirash.mirashcalendarwidget.utils.Utils.startAlarmManagerService;

/**
 * @author Mirash
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        updateWidgets(context);
        startAlarmManagerService(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        cancelAlarmManagerService(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        updateWidgets(context);
        if (!Constants.CLOCK_WIDGET_UPDATE_ACTION.equals(intent.getAction())) {
            cancelAlarmManagerService(context);
            startAlarmManagerService(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, WidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.content_layout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateWidgets(Context context) {
        ComponentName appWidget = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int ids[] = appWidgetManager.getAppWidgetIds(appWidget);
        for (int appWidgetID : ids) {
            updateAppWidget(context, appWidgetManager, appWidgetID);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Calendar calendar = Calendar.getInstance();
        String dateString = DATE_FORMAT.format(calendar.getTime());
        Spannable dateSpannableString = new SpannableString(dateString);
        int dayTextIndex = dateString.indexOf(",") + 1;
        dateSpannableString.setSpan(new ForegroundColorSpan(Utils.getDayOfWeekColor(calendar)),
                dayTextIndex, dateString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        updateViews.setTextViewText(R.id.widget_date_label, dateSpannableString);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }
}
