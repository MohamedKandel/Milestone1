package com.mkandeel.currencyconversion.Helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HelperClass {
    private static HelperClass helper;
    private Fragment fragment;
    private Context context;
    private Activity activity;

    private HelperClass(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.requireActivity();
        this.context = fragment.requireContext();
    }

    public static HelperClass getInstance(Fragment fragment) {
        if (helper == null) {
            helper = new HelperClass(fragment);
        }
        return helper;
    }

    public void navigate(int layoutID, @Nullable Bundle bundle) {
        if (bundle == null) {
            NavController navController = Navigation.findNavController(fragment.requireView());
            navController.navigate(layoutID);
        } else {
            Navigation.findNavController(fragment.requireView())
                    .navigate(layoutID, bundle);
        }
    }

    public void onBackPressed(int layoutID) {
        fragment.requireActivity().getOnBackPressedDispatcher()
                .addCallback(fragment.getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        helper.navigate(layoutID, null);
                    }
                });
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public String getLastThreeDays(Date currentDate,int daysToSubtract) {
        String newDate;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String current = sdf.format(currentDate);
            LocalDate originalDate;

            originalDate = LocalDate.parse(current);
            // Subtracting n days from the original date
            LocalDate DateAfterSubtract = originalDate.minusDays(daysToSubtract);
            newDate = DateAfterSubtract+"";
        } else {
            newDate = "NOT SUPPORTED METHOD";
        }
        return newDate;
    }

    public List<String> getMapKeys(Map<String,Double> map) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String,Double> entry:map.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    public String getKeyByValue(Map<String,Double> map,double value) {
        String key = "";
        for (Map.Entry<String,Double> entry:map.entrySet()) {
            if (value == entry.getValue()) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

    public List<String> getMapValues(Map<String,Double> map) {
        List<String> values = new ArrayList<>();
        for (Map.Entry<String,Double> entry:map.entrySet()) {
            values.add(String.valueOf(entry.getValue()));
        }
        return values;
    }
}
