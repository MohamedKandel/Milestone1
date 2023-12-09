package com.mkandeel.currencyconversion.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mkandeel.currencyconversion.R;
import com.mkandeel.currencyconversion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment navhost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController controller = NavHostFragment.findNavController(navhost);
        BottomNavigationView btm = findViewById(R.id.bottomNavView);

        NavigationUI.setupWithNavController(btm, controller);

        btm.setBackground(null);

        btm.setOnItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if (id == R.id.home) {
                        controller.navigate(R.id.currencyConversionFragment);
                    } else if (id == R.id.history) {
                        controller.navigate(R.id.historyFragment);
                    }
                    return true;
                });
    }
}