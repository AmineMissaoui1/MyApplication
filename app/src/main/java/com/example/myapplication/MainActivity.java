package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.RuleBasedCollator;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing :
        Button setBtn=findViewById(R.id.setBtn);
        EditText editText=findViewById(R.id.editText);
        DatePicker datePicker =findViewById(R.id.DatePicker);
        TimePicker timePicker = findViewById(R.id.TimePicker);

        //Create channel for API 26 or higher
        createNotificationChannel();

        //Initializing AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Setting the task when set Button is tapped
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent and sending task put in editText
                Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
                intent.putExtra("message", editText.getText().toString());
                //Pending intent to broadcast for notification
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_IMMUTABLE);

                //Getting Date and time from DatePicker and TimePicker
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                int Day = datePicker.getDayOfMonth();
                int Month = datePicker.getMonth();
                int Year = datePicker.getYear();

                // Create time of alarm
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.YEAR,Year);
                startTime.set(Calendar.MONTH,Month);
                startTime.set(Calendar.DAY_OF_MONTH,Day);
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                Toast.makeText(MainActivity.this, "Reminder Set !", Toast.LENGTH_SHORT).show();



            }
        });


    }
//Creating the Notification channel for API 26 or Higher
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= 26) {
            //Name
            CharSequence name="ReminderChannel";
            //Description
            String description = "Channel for Reminder";
            //Importance (Priority)
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // Initializing notification channel
            NotificationChannel channel =new NotificationChannel("chan",name,importance);
            channel.setDescription(description);
            //Creating the channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
