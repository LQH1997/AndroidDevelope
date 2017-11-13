package com.example.andrew.lab5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class myWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget, pi);
        ComponentName me = new ComponentName(context, myWidget.class);
        appWidgetManager.updateAppWidget(me, updateViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context,Intent intent){
        super.onReceive(context, intent);
        if(intent.getAction().equals("launchBroadcast")) {
            Log.i("Widget", "get");
            ListItems thisItem = (ListItems) intent.getSerializableExtra("myData");
            CharSequence showStr = thisItem.getName()+"¥"+thisItem.getPrice()+"!";
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
            remoteViews.setTextViewText(R.id.appwidget_text, showStr);
            remoteViews.setImageViewResource(R.id.widgetImage, context.getResources().getIdentifier(thisItem.getSrc(), "drawable", "com.example.andrew.lab5"));
            ComponentName componentName = new ComponentName(context, myWidget.class);
            appWidgetManager.updateAppWidget(componentName,remoteViews);

            Intent temp = new Intent();
            temp.setClass(context,item_info.class);
            temp.putExtra("myData", thisItem);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,temp,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent);

            appWidgetManager.updateAppWidget(new ComponentName(context,myWidget.class),remoteViews);

        }
        if(intent.getAction().equals("DynamicNotification")) {
            //
            Log.i("widget", "item into cart");
        }
    }
}

