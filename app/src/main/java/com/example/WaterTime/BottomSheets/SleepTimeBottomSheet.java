package com.example.WaterTime.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.WaterTime.R;
import com.example.WaterTime.TinyDb.TinyDB;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

public class SleepTimeBottomSheet extends BottomSheetDialogFragment implements TimePickerDialog.OnTimeSetListener
{

    TimePickerDialog sleepTimePicker;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sleep_time_bottom_sheet, container, false);

        TinyDB tinyDB = new TinyDB(getActivity());







        sleepTimePicker = TimePickerDialog.newInstance(
                SleepTimeBottomSheet.this,
                    tinyDB.getInt("sleepTimeHour"),
                    tinyDB.getInt("sleepTimeMinute"),
                    true
            );

        sleepTimePicker.setOkText("حله");
        sleepTimePicker.setCancelColor("#212121");
        sleepTimePicker.setCancelText("بیخیال");
        sleepTimePicker.setOkColor("#FB6090");
        sleepTimePicker.setTitle("ساعت خوابت رو انتخاب کن");
        sleepTimePicker.setAccentColor("#333652");



        sleepTimePicker.show(getChildFragmentManager(),"TAG");





        return view;

    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)
    {
        TinyDB tinyDB = new TinyDB(getActivity());

        tinyDB.putInt("sleepTimeHour",hourOfDay);
        tinyDB.putInt("sleepTimeMinute",minute);


    }
}
