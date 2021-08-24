package com.example.WaterTime.Activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.WaterTime.BottomSheets.SleepTimeBottomSheet;
import com.example.WaterTime.BottomSheets.RemindTimeBottomSheet;
import com.example.WaterTime.BottomSheets.WakeUpTimeBottomSheet;
import com.example.WaterTime.BottomSheets.WeightBottomSheet;
import com.example.WaterTime.BuildConfig;
import com.example.WaterTime.R;
import com.example.WaterTime.TinyDb.TinyDB;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class SettingsActivity extends AppCompatActivity
{

    SwitchMaterial exerciseSwitch;
    MaterialCardView weightCardView,timeCardView,glassSizeCardView,emailCardView;
    MaterialTextView versionNumber,weightTextValue,timeTextValue,glassSizeTextView,sleepTimeTextValue,wakeUpTimeTextValue;
    ImageView birdDrinkWater;
    String version = BuildConfig.VERSION_NAME;





    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();
        }

        TinyDB tinyDB = new TinyDB(getApplicationContext());


        init();


        versionNumber.setText("نسخه "+version);





        //if weight value changed,progressBar chang its value by new weight
        tinyDB.putInt("progressBar",tinyDB.getInt("progressBar"));


        weightTextValue.setText("    "+(int) tinyDB.getDouble("weight")+"");
        weightCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WeightBottomSheet weightBottomSheet = new WeightBottomSheet();
                weightBottomSheet.show(getSupportFragmentManager(),"TAG");
                weightTextValue.setText("    "+(int) tinyDB.getDouble("weight")+"");



            }
        });

        timeTextValue.setText(tinyDB.getInt("minute")+"  دقیقه  ");
        timeCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RemindTimeBottomSheet remindTimeBottomSheet = new RemindTimeBottomSheet();
                remindTimeBottomSheet.show(getSupportFragmentManager(),"TAG");
                timeTextValue.setText(tinyDB.getInt("minute")+"  دقیقه  ");

            }
        });






        exerciseSwitch.setChecked(tinyDB.getBoolean("areYouExercise"));
        exerciseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    tinyDB.putBoolean("areYouExercise",true);
                    exerciseTimeDialog();

                }else {
                    tinyDB.putBoolean("areYouExercise",false);

                }
            }
        });




       glassSizeTextView.setText("    "+tinyDB.getInt("glassSize")+"ml");
        glassSizeCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                glassSizeDialog();
                glassSizeTextView.setText("    "+tinyDB.getInt("glassSize")+"ml");

            }
        });

        sleepTimeTextValue.setText("از  "+tinyDB.getInt("sleepTimeHour")+":"+tinyDB.getInt("sleepTimeMinute")+" ");
        sleepTimeTextValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SleepTimeBottomSheet sleepTimeBottomSheet = new SleepTimeBottomSheet();
                sleepTimeBottomSheet.show(getSupportFragmentManager(),"TAG");
                sleepTimeTextValue.setText("از  "+tinyDB.getInt("sleepTimeHour")+":"+tinyDB.getInt("sleepTimeMinute")+" ");
            }
        });


        wakeUpTimeTextValue.setText("    تا   "+tinyDB.getInt("wakeUpTimeHour")+":"+tinyDB.getInt("wakeUpTimeMinute"));
        wakeUpTimeTextValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WakeUpTimeBottomSheet wakeUpTimeBottomSheet = new WakeUpTimeBottomSheet();
                wakeUpTimeBottomSheet.show(getSupportFragmentManager(),"TAG");
                wakeUpTimeTextValue.setText("    تا   "+tinyDB.getInt("wakeUpTimeHour")+":"+tinyDB.getInt("wakeUpTimeMinute"));

            }
        });








        emailCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailAddresses = "ali.shojasani@tutamail.com";
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto: "+emailAddresses));
                startActivity(Intent.createChooser(sendEmail,"ارسال با"));
            }
        });




        birdDrinkWater.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content),"من تزئینیم کاری ازم برنمیاد",Snackbar.LENGTH_LONG);
                snackBar.show();

            }
        });




    }


    private void init()
    {

        exerciseSwitch       = findViewById(R.id.exercise_switch);
        weightCardView       = findViewById(R.id.weight_cardview);
        emailCardView        = findViewById(R.id.email_card_view);
        versionNumber        = findViewById(R.id.version_number);
        birdDrinkWater       = findViewById(R.id.bird_drink_water);
        timeCardView         = findViewById(R.id.time_cardview);
        weightTextValue      = findViewById(R.id.weight_text_valu);
        timeTextValue        = findViewById(R.id.time_text_value);
        glassSizeCardView    = findViewById(R.id.glass_size_card_view);
        glassSizeTextView    = findViewById(R.id.glass_size_text_value);
        sleepTimeTextValue   = findViewById(R.id.sleep_time_text_value);
        wakeUpTimeTextValue  = findViewById(R.id.wake_up_time_text_value);



    }




    private void exerciseTimeDialog()
    {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        String[] exerciseTime = {"۰۰:۳۰", "۰۱:۰۰", "۱:۳۰", "۰۲:۰۰","۰۳:۰۰"};

        //For each 30 minute exercise should add 355ml to userGoalMl
        final  int ADD_ML = 355;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("مقدار زمان ورزش در روز");
        builder.setIcon(R.drawable.ic_emojione_person_lifting_weights_medium_skin_tone);
        builder.setItems(exerciseTime, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if("۰۰:۳۰".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML);
                    toastMessage("۰۰:۳۰");

                }
                else if("۰۱:۰۰".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*2);
                    toastMessage("۰۱:۰۰");
                }
                else if("۱:۳۰".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*3);
                    toastMessage("۱:۳۰");
                }
                else if("۰۲:۰۰".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*4);
                    toastMessage("۰۲:۰۰");
                }
                else  if("۰۳:۰۰".equals(exerciseTime[which]))
                {
                    tinyDB.putInt("addToUserGoal",ADD_ML*6);
                    toastMessage("۰۳:۰۰");

                }



            }
        });
        builder.show();
    }

    private  void toastMessage(String time)
    {
        switch (time)
        {
            case "۰۰:۳۰":
                Snackbar snackBar1 = Snackbar.make(findViewById(android.R.id.content),"۳۰ دقیقه انتخاب شد",Snackbar.LENGTH_LONG);
                snackBar1.show();
                break;
            case "۰۱:۰۰":
                Snackbar snackBar2 = Snackbar.make(findViewById(android.R.id.content)," ۱ ساعت انتخاب شد",Snackbar.LENGTH_LONG);
                snackBar2.show();
                break;
            case "۱:۳۰":
                Snackbar snackBar3 = Snackbar.make(findViewById(android.R.id.content)," ۱:۳۰ دقیقه انتخاب شد",Snackbar.LENGTH_LONG);
                snackBar3.show();
                break;
            case "۰۲:۰۰":
                Snackbar snackBar4 = Snackbar.make(findViewById(android.R.id.content)," ۲ ساعت انتخاب شد",Snackbar.LENGTH_LONG);
                snackBar4.show();
                break;
            case "۰۳:۰۰":
                Snackbar snackBar5 = Snackbar.make(findViewById(android.R.id.content)," 3 ساعت انتخاب شد",Snackbar.LENGTH_LONG);
                snackBar5.show();
                break;



        }
    }
    private void glassSizeDialog()
    {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        String[] glassSize = {"۱۰۰  ml", "۲۵۰  ml", "۵۰۰  ml", "۷۵۰ ml"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ظرفیت لیوان");
        builder.setIcon(R.drawable.ic_glass_of_water);
        builder.setItems(glassSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if("۱۰۰  ml".equals(glassSize[which]))
                {
                    tinyDB.putInt("glassSize",100);
                }
                else if("۲۵۰  ml".equals(glassSize[which]))
                {
                    tinyDB.putInt("glassSize",250);
                }
                else if("۵۰۰  ml".equals(glassSize[which]))
                {
                    tinyDB.putInt("glassSize",500);
                }
                else if("۷۵۰ ml".equals(glassSize[which]))
                {
                    tinyDB.putInt("glassSize",750);
                }


            }
        });
        builder.show();
    }


    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save);
        builder.setMessage(R.string.saveChanges);
        builder.setPositiveButton("ذخیره", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                System.exit(0);
            }
        });
        builder.show();
    }





}