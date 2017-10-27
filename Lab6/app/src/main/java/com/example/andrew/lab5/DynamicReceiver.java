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
        //Log.i("TAG", "BBB");
        //Toast.makeText(context, "hail", Toast.LENGTH_SHORT).show();
        //context.unregisterReceiver(this);
        ListItems notiItem = (ListItems) intent.getSerializableExtra("myData");
        //Toast.makeText(context, notiItem.getName() + " " +notiItem.getPrice(), Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.setClass(context.getApplicationContext(), shopping_cart.class);
        //ArrayList<ListItems> aaa = new ArrayList<ListItems>();
        //notiItem.setNum(1);
        //aaa.add(notiItem);
        //intent1.putExtra("myData", aaa);
        //intent1.setAction(Intent.ACTION_MAIN);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manger = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder onStartNotiBuilder = new NotificationCompat.Builder(context);
        onStartNotiBuilder.setContentTitle("马上下单")
                .setContentText(notiItem.getName() + "已添加到购物车")
                .setTicker("aaa")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(notiItem.getSrc(), "drawable", "com.example.andrew.lab5")))
                .setSmallIcon(R.drawable.devondale)
                .setContentIntent(pIntent);
        Notification notify = onStartNotiBuilder.build();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        //notify.c;
        manger.notify(0, notify);

    }

}
