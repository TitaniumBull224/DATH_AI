package com.example.internet.ui.profile;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.internet.DatabaseHelper;

public class ProfileViewModel extends ViewModel {
    private String email;
    private DatabaseHelper databaseHelper;

    public ProfileViewModel(Application application) {
        databaseHelper = new DatabaseHelper(application);
    }

    public String getEmail() {
        email = databaseHelper.getEmail();
        return email;
    }
}