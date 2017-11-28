package com.uhuru.dashboard;

import android.app.AlarmManager;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppWidgetProvider{

    private static final String LOG = "uhuru";
    private PendingIntent service = null;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        //System.out.println("onUpdate");

        final int N = appWidgetIds.length;

    for (int i=0; i<N; i++) {

        int appWidgetId = appWidgetIds[i];

        // Intent pour l'activity
        //Intent intent = new Intent(context, DetailsActivity.class);
        Intent intent = new Intent(context, DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Onclick listener sur le bouton
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
        views.setOnClickPendingIntent(R.id.details, pendingIntent);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

       /* final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Calendar TIME = Calendar.getInstance();
        TIME.set(Calendar.MINUTE, 0);
        TIME.set(Calendar.SECOND, 0);
        TIME.set(Calendar.MILLISECOND, 0);*/

        /*final Intent i = new Intent(context, UpdateWidgetService.class);

        if (service == null)
        {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000, service);*/


    }

    @Override
    public void onDisabled(Context context)
    {
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        m.cancel(service);
    }
}