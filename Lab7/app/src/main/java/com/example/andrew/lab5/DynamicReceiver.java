package com.example.andrew.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/26.
 */

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ListItems notiItem = (ListItems) intent.getSerializableExtra("myData");
        Intent intent1 = new Intent();
        intent1.setClass(context.getApplicationContext(), shopping_cart.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manger = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder onStartNotiBuilder = new NotificationCompat.Builder(context);
        onStartNotiBuilder.setContentTitle("马上下单")
                .setContentText(notiItem.getName() + "已添加到购物车")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(notiItem.getSrc(), "drawable", "com.example.andrew.lab5")))
                .setSmallIcon(R.drawable.devondale)
                .setContentIntent(pIntent);
        Notification notify = onStartNotiBuilder.build();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        //notify.c;
        manger.notify(0, notify);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
        remoteViews.setTextViewText(R.id.appwidget_text,notiItem.getName()+"已加入购物车");
        remoteViews.setImageViewResource(R.id.widgetImage,context.getResources().getIdentifier(notiItem.getSrc(),
                "drawable", "com.example.andrew.lab5"));
        ComponentName componentName = new ComponentName(context,myWidget.class);
        appWidgetManager.updateAppWidget(componentName,remoteViews);
        Intent temp1 = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,temp1,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent2);
        appWidgetManager.updateAppWidget(new ComponentName(context,myWidget.class),remoteViews);

    }

}
