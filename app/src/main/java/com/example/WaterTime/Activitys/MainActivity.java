package com.example.WaterTime.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.WaterTime.Services.TurnOffNotification;
import com.example.WaterTime.Services.SendNotification;
import com.example.WaterTime.R;
import com.example.WaterTime.TinyDb.TinyDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.skydoves.progressview.OnProgressChangeListener;
import com.skydoves.progressview.ProgressView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity
{
    protected double maxMl;
    protected int userGoalInML;
    protected double userWeight;
    protected int currentGlassSize;
    protected final double constantNumber = 0.033;
    protected LottieAnimationView waterAnimation,partyPopper;
    protected ProgressView progressBar;
    protected MaterialButton drinkButton;
    protected MaterialTextView userGoalTextField;
    protected MaterialTextView glassSizeTextView;
    protected BottomNavigationView bottomNavigationView;
    protected MediaPlayer waterEffect;


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onStart()
    {
        super.onStart();
        sendNotification();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        sendNotification();




        if(tinyDB.getBoolean("OneTimeDialog"))
        {
            defaultValueDialog();
            tinyDB.putBoolean("OneTimeDialog",false);
        }



        init();






        currentGlassSize = tinyDB.getInt("glassSize");
        userWeight = tinyDB.getDouble("weight");


       /* Calculate How much water user should drink in Ml
        The formula for calculating user water is:
        In L = (UserWeight) * (0.033);
        In Ml = (UserWeight) * (0.033) * (1000);

        */

        if(tinyDB.getBoolean("areYouExercise"))
        {
            maxMl = (userWeight * constantNumber*1000)+(tinyDB.getInt("addToUserGoal"));
            tinyDB.putBoolean("areYouExercise",true);

        }else
            {
                maxMl = userWeight * constantNumber*1000;
                tinyDB.putBoolean("areYouExercise",false);

            }
        userGoalInML = (int)(Math.round( maxMl / 100.0) * 100);
        userGoalTextField.setText(userGoalInML+" ml");





        //progressBar Setting
        progressBar.setMax(userGoalInML);
        progressBar.setAnimating(true);
        progressBar.setProgressFromPrevious(true);
        progressBar.setOnProgressChangeListener(new OnProgressChangeListener()
        {
            @Override
            public void onChange(float v)
            {
                float percent = (v / userGoalInML)*100;
                progressBar.setLabelSize(20);
                tinyDB.putFloat("progressBarPercent",percent);
                progressBar.setLabelText((int)tinyDB.getFloat("progressBarPercent")+"%");
                if(progressBar.isProgressedMax())
                {
                    MediaPlayer smallCrowd = MediaPlayer.create(getApplicationContext(),R.raw.small_crowd);
                    partyPopper.setVisibility(View.VISIBLE);
                    partyPopper.playAnimation();
                    smallCrowd.start();
                }

            }
        });







        drinkButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setProgress(currentGlassSize + tinyDB.getInt("progressBar"));
                tinyDB.putInt("progressBar", (int) (progressBar.getProgress()));



                waterAnimation.playAnimation();
                drinkButton.setClickable(false);
                MediaPlayer waterPouring = MediaPlayer.create(getApplicationContext(),R.raw.water_pouring_into_glass);
                waterPouring.start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        drinkButton.setClickable(true);
                    }
                },1500);


            }
        });


        glassSizeTextView.setText(tinyDB.getInt("glassSize")+" ml");





        //Bottom Navigation Setting
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {

                Intent setting  = new Intent(getApplicationContext(), SettingsActivity.class);
                switch (item.getItemId())
                {
                    case R.id.setting:
                        startActivity(setting);
                        break;

                }
                return true;
            }
        });



    }
    private void init()
    {
        waterAnimation       =  findViewById(R.id.water_animation);
        progressBar          =  findViewById(R.id.progress_bar);
        drinkButton          =  findViewById(R.id.drink_button);
        userGoalTextField    =  findViewById(R.id.userGoaltextfield);
        bottomNavigationView =  findViewById(R.id.bootom_navigation);
        glassSizeTextView    =  findViewById(R.id.glass_size_text_view);
        partyPopper          =  findViewById(R.id.party_popper);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification()
    {
        
        TinyDB tinyDB = new TinyDB(this);



        CharSequence name = "water";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyDrink",name,importance);
        channel.setDescription("Channel for water reminder");
        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.enableVibration(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent(MainActivity.this, SendNotification.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        //Time that notification will goes ON
        Calendar notificationON = Calendar.getInstance();
        notificationON.set(Calendar.HOUR_OF_DAY, tinyDB.getInt("wakeUpTimeHour"));
        notificationON.set(Calendar.MINUTE, tinyDB.getInt("wakeUpTimeMinute"));

        alarmManager.set(AlarmManager.RTC_WAKEUP,notificationON.getTimeInMillis(),pendingIntent);



        Intent cancelSendNotification = new Intent(MainActivity.this, TurnOffNotification.class);

        PendingIntent cancelSendNotificationPendingIntent = PendingIntent.getService(MainActivity.this,0,cancelSendNotification,PendingIntent.FLAG_UPDATE_CURRENT);

        //Time that notification will goes OFF
        Calendar notificationOFF = Calendar.getInstance();
        notificationOFF.set(Calendar.HOUR_OF_DAY, tinyDB.getInt("sleepTimeHour"));
        notificationOFF.set(Calendar.MINUTE, tinyDB.getInt("sleepTimeMinute"));

        alarmManager.set(AlarmManager.RTC_WAKEUP,notificationOFF.getTimeInMillis(),cancelSendNotificationPendingIntent);

    }











    private void defaultValueDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("توجه");
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setMessage(R.string.faildRegister);
        builder.show();



    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume()
    {
        super.onResume();
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        progressBar.setProgress(tinyDB.getInt("progressBar"));
        sendNotification();


    }



    
}