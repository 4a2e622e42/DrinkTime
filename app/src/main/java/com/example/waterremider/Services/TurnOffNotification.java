package com.example.waterremider.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.waterremider.TinyDb.TinyDB;

public class TurnOffNotification extends Service
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;

    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        turnOffNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        turnOffNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }

    private void turnOffNotification()
    {

        TinyDB tinyDB = new TinyDB(this);
        Intent intent = new Intent(TurnOffNotification.this, SendNotification.class);
        PendingIntent pendingIntent = PendingIntent.getService(TurnOffNotification.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) TurnOffNotification.this.getSystemService(Context.ALARM_SERVICE);
        tinyDB.putInt("progressBar",0);
        alarmManager.cancel(pendingIntent);



    }
}
