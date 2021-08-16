package com.example.waterremider.Services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.waterremider.R;
import com.example.waterremider.TinyDb.TinyDB;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendNotification extends Service
{
    private NotificationManager mNM;



    public class LocalBinder extends Binder
    {
        SendNotification getService()
        {
            sendNotification();
            return SendNotification.this;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        TinyDB tinyDB = new TinyDB(this);
        final MediaPlayer[] waterEffect = new MediaPlayer[1];
        long minuteInMillisecond;
        int minute = tinyDB.getInt("minute");


        if(minute>=51)
        {
            minuteInMillisecond = 50*60*1000;

        }else
        {
            minuteInMillisecond = minute*60*1000;

        }

        //Send Notification


        final Handler handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this,minuteInMillisecond);
                waterEffect[0] = MediaPlayer.create(getApplicationContext(), R.raw.water_pouring_into_glass);
                sendNotification();
                waterEffect[0].start();

            }
        },minuteInMillisecond);


     /*   ScheduledExecutorService scheduler =  Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate
                (new Runnable()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run()
                    {
                        waterEffect[0] = MediaPlayer.create(getApplicationContext(), R.raw.water_pouring_into_glass);
                        sendNotification();
                        waterEffect[0].start();
                    }
                }, minuteInMillisecond, minuteInMillisecond, TimeUnit.MILLISECONDS);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TinyDB tinyDB = new TinyDB(this);
        final MediaPlayer[] waterEffect = new MediaPlayer[1];
        long minuteInMillisecond;
        int minute = tinyDB.getInt("minute");

        if(minute>=51)
        {
            minuteInMillisecond = 50*60*1000;

        }else
        {
            minuteInMillisecond = minute*60*1000;

        }
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this,minuteInMillisecond);
                waterEffect[0] = MediaPlayer.create(getApplicationContext(), R.raw.water_pouring_into_glass);
                sendNotification();
                waterEffect[0].start();

            }
        },minuteInMillisecond);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        TinyDB tinyDB = new TinyDB(this);
        final MediaPlayer[] waterEffect = new MediaPlayer[1];
        long minuteInMillisecond;
        int minute = tinyDB.getInt("minute");

        if(minute>=51)
        {
            minuteInMillisecond = 50*60*1000;

        }else
        {
            minuteInMillisecond = minute*60*1000;

        }
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this,minuteInMillisecond);
                waterEffect[0] = MediaPlayer.create(getApplicationContext(), R.raw.water_pouring_into_glass);
                sendNotification();
                waterEffect[0].start();

            }
        },minuteInMillisecond);
    }



    @Override
    public IBinder onBind(Intent intent)
    {

        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();


    private void sendNotification()
    {

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"notifyDrink")
                .setContentTitle("وقت نوشیدنه")
                .setContentText("پاشو یه لیوان آب بزن بر بدن")
                .setSmallIcon(R.drawable.ic_drop)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        notificationManager.notify(100,builder.build());

    }
}