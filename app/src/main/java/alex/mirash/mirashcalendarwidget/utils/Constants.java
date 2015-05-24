package alex.mirash.mirashcalendarwidget.utils;

import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Mirash
 */
public class Constants {
    public static final long TIME_UPDATE_INTERVAL = 1000 * 10 * 60 * 24L;
    public static final String CLOCK_WIDGET_UPDATE_ACTION = "MIRASH_WIDGET_CLOCK_UPDATE_ACTION";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, E");
    public static final int DAY_TEXT_COLOR_DEFAULT = Color.parseColor("#C2FFFE");
    public static final int DAY_TEXT_COLOR_WEEKEND = Color.parseColor("#FFDFDE");
}
