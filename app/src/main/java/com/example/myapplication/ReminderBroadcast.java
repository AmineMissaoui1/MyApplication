package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    //we add extends BroadcastReceiver and create  onReceive inorder to receive the Broadcast and create the Notification
    @Override
    public void onReceive(Context context, Intent intent){
        //Getting the task input in editText
        String message = intent.getStringExtra("message");
        //Creating the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chan")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Reminder !")
                .setContentText(message)
                //.setSound()
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Sending the Notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100,builder.build());

    }
}
