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

public class WakeUpTimeBottomSheet extends BottomSheetDialogFragment implements TimePickerDialog.OnTimeSetListener
{


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sleep_time_bottom_sheet, container, false);

        TinyDB tinyDB = new TinyDB(getActivity());







        TimePickerDialog wakeUpTimePicker = TimePickerDialog.newInstance(
                WakeUpTimeBottomSheet.this,
                tinyDB.getInt("wakeUpTimeHour"),
                tinyDB.getInt("wakeUpTimeMinute"),
                true
        );
        wakeUpTimePicker.setOkText("حله");
        wakeUpTimePicker.setCancelColor("#212121");
        wakeUpTimePicker.setCancelText("بیخیال");
        wakeUpTimePicker.setOkColor("#FB6090");
        wakeUpTimePicker.setTitle("ساعت بیدار شدنت رو انتخاب کن");
        wakeUpTimePicker.setAccentColor("#90ADC6");


        wakeUpTimePicker.show(getChildFragmentManager(),"TAG");




        return view;

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)
    {
        TinyDB tinyDB = new TinyDB(getActivity());

        tinyDB.putInt("wakeUpTimeHour",hourOfDay);
        tinyDB.putInt("wakeUpTimeMinute",minute);


    }

}

