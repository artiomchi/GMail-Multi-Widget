package org.flexlabs.widgets.gmailmulti;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by IntelliJ IDEA.
 * User: Artiom Chilaru
 * Date: 19/06/11
 * Time: 10:08
 */
public class WidgetLite extends AppWidgetProvider {
    private int gMailCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (Intent.ACTION_PROVIDER_CHANGED.equals(intent.getAction())) {
            if (intent.getDataString().contains("/unread/^iim") && intent.getBooleanExtra("getAttention", false)) {
                gMailCount = intent.getIntExtra("count", 0);
                updateWidget(context);
            }
        }
        if (Constants.ACTION_WIDGET_UPDATE.equals(intent.getAction())) {
            updateWidget(context);
        }
    }

    private void updateWidget(Context context) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(context, WidgetLite.class));
        onUpdate(context, widgetManager, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //GMailWizApplication.registerReceiver(context);

        Log.d("FlexLabs", "onUpdate mail count: " + GMailWizApplication.mailCount);
        for (int widgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_lite);

            views.setTextViewText(R.id.mailCount, String.valueOf(gMailCount));

            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }
}
