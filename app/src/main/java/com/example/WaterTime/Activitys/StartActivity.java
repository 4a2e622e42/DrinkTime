package com.example.WaterTime.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;

import com.example.WaterTime.R;
import com.example.WaterTime.TinyDb.TinyDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;


public class StartActivity extends AppCompatActivity
{
    NumberPicker weightPicker,minutePicker;
    MaterialButton nextButton;
    MediaPlayer bubbleEffect;
    MaterialCheckBox athleticCheckBox;
    int minute,weight;





    //Check if this Activity showed once or not
    @Override
    protected void onStart()
    {
        super.onStart();
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        tinyDB.putInt("glassSize",100);


        //Default Sleep Time
        tinyDB.putInt("sleepTimeHour",23);
        tinyDB.putInt("sleepTimeMinute",0);


        //Default WakeUpTime
        tinyDB.putInt("wakeUpTimeHour",10);
        tinyDB.putInt("wakeUpTimeMinute",0);




    }





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_start);



        TinyDB tinyDB = new TinyDB(getApplicationContext());

        weightPicker     = findViewById(R.id.weight_picker);
        minutePicker     = findViewById(R.id.minute_picker);
        nextButton        = findViewById(R.id.next_button);
        athleticCheckBox  = findViewById(R.id.athletic_check_box);




        SharedPreferences preferences = getSharedPreferences("PREFERNCE",MODE_PRIVATE);

        String firstTime = preferences.getString("firstTime","");
        String defaultValues = preferences.getString("defaultValues","");

        SharedPreferences.Editor editor = preferences.edit();

        if(firstTime.equals("") && firstTime.equals("No") )
        {

            editor.putString("firstTime", "No");
            editor.apply();

        }else if(defaultValues.equals(""))
        {
            //This dialog only show once if user dose not pick any value
            //Main code is in the MainActivity
            tinyDB.putBoolean("OneTimeDialog",true);


            tinyDB.putDouble("weight", 50);
            tinyDB.putInt("minute",30);
            editor.putString("defaultValues","yes");
            editor.apply();
        }else
        {

            lunchHomeScreen();

        }





        athleticCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    tinyDB.putBoolean("areYouExercise",true);
                    exerciseTimeDialog();

                }else if(!isChecked)
                {
                    tinyDB.putBoolean("areYouExercise",false);

                }

            }
        });










        weightPicker.setMinValue(0);
        weightPicker.setMaxValue(250);
        weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                weight = newVal;
                tinyDB.putDouble("weight", newVal);


                bubbleEffect = MediaPlayer.create(getApplicationContext(),R.raw.bubble_effect);
                bubbleEffect.start();
            }
        });







        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(60);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                minute = newVal;
                tinyDB.putInt("minute", newVal);


                bubbleEffect = MediaPlayer.create(getApplicationContext(),R.raw.bubble_effect);
                bubbleEffect.start();
            }
        });








       nextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
                    if(weight ==  0 && minute == 0)
                    {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"???? ?????? ???????????? ???????? ???? ???????? ???? ?????????? :(\n???? ?????? ?????????? ???????? ???????? ????",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else if(weight == 0 )
                    {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"???????? ???? ???????????? ????",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                   else if(minute == 0)
                    {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"???????? ?????????????? ???? ???????????? ?????????? :(",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else
                    {
                        //lunch  MainActivity
                        Intent next_page = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(next_page);
                        finish();

                    }




           }
       });



    }



    private void lunchHomeScreen()
    {

        Intent homeScreen = new Intent(StartActivity.this,MainActivity.class);
        homeScreen.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(homeScreen);
        overridePendingTransition(0, 0);
        StartActivity.super.finish();

    }
    private void exerciseTimeDialog()
    {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        String[] exerciseTime = {"????:????", "????:????", "??:????", "????:????","????:????"};

        //For each 30 minute exercise should add 355ml to userGoalMl
        final  int ADD_ML = 355;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("?????????? ???????? ???????? ???? ??????");
        builder.setIcon(R.drawable.ic_emojione_person_lifting_weights_medium_skin_tone);
        builder.setItems(exerciseTime, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if("????:????".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML);
                    toastMessage("????:????");

                }
                else if("????:????".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*2);
                    toastMessage("????:????");
                }
                else if("??:????".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*3);
                    toastMessage("??:????");
                }
                else if("????:????".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*4);
                    toastMessage("????:????");
                }
                else  if("????:????".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*6);
                    toastMessage("????:????");

                }


            }
        });
        builder.show();
    }

    private  void toastMessage(String time)
    {
        switch (time)
        {
            case "????:????":
                Snackbar snackBar1 = Snackbar.make(findViewById(android.R.id.content),"???? ?????????? ???????????? ????",Snackbar.LENGTH_LONG);
                snackBar1.show();
                break;
            case "????:????":
                Snackbar snackBar2 = Snackbar.make(findViewById(android.R.id.content)," ?? ???????? ???????????? ????",Snackbar.LENGTH_LONG);
                snackBar2.show();
                break;
            case "??:????":
                Snackbar snackBar3 = Snackbar.make(findViewById(android.R.id.content)," ??:???? ?????????? ???????????? ????",Snackbar.LENGTH_LONG);
                snackBar3.show();
                break;
            case "????:????":
                Snackbar snackBar4 = Snackbar.make(findViewById(android.R.id.content)," ?? ???????? ???????????? ????",Snackbar.LENGTH_LONG);
                snackBar4.show();
                break;
            case "????:????":
                Snackbar snackBar5 = Snackbar.make(findViewById(android.R.id.content)," 3 ???????? ???????????? ????",Snackbar.LENGTH_LONG);
                snackBar5.show();
                break;



        }
    }




}



