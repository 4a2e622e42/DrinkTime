package com.example.waterremider.BottomSheets;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.waterremider.TinyDb.TinyDB;
import com.example.waterremider.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class WeightBottomSheet extends BottomSheetDialogFragment
{
    NumberPicker weight;
    MediaPlayer bubbleEffect;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.weight_bottom_sheet,container,false);
        TinyDB tinyDB = new TinyDB(getActivity());

        weight = view.findViewById(R.id.weight_bottom_sheet);

        weight.setValue((int)tinyDB.getDouble("weight"));
        weight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

                tinyDB.putDouble("weight",newVal);
                bubbleEffect = MediaPlayer.create(getContext(),R.raw.bubble_effect);
                bubbleEffect.start();

            }
        });




        return view;


    }




}
