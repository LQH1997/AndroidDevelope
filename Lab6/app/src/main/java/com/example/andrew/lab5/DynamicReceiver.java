package com.example.andrew.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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

    }

}
