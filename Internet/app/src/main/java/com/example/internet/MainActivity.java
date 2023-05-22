package com.example.internet;

import android.os.Bundle;
import android.widget.TextView;

import com.example.internet.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.internet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the email from the Intent
        String email = getIntent().getStringExtra("email");

        // Create a Bundle and put the email in it
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_schedule,
                R.id.navigation_profile,
                R.id.navigation_notifications)
                .build();
         */
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        binding.navView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
                return true;
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_dashboard);
                return true;
            } else if (item.getItemId() == R.id.navigation_schedule) {
                navController.navigate(R.id.navigation_schedule);
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                navController.navigate(R.id.navigation_profile, bundle);
                return true;
            } else if (item.getItemId() == R.id.navigation_notifications) {
                navController.navigate(R.id.navigation_notifications);
                return true;
            } else
                return false;
        });



    }

}