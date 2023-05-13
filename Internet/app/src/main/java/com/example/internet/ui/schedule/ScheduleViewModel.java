package com.example.internet.ui.schedule;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ScheduleViewModel extends ViewModel {
    private MutableLiveData<List<Schedule>> schedules;
    private ScheduleDatabaseHelper scheduleDatabaseHelper;

    public ScheduleViewModel(Application application) {
        schedules = new MutableLiveData<>();
        scheduleDatabaseHelper = new ScheduleDatabaseHelper(application);
        loadSchedules();
    }

    public LiveData<List<Schedule>> getSchedules() {
        return schedules;
    }

    public void createSchedule(Schedule schedule) {
        scheduleDatabaseHelper.createSchedule(schedule);
        loadSchedules();
    }

    public void updateSchedule(Schedule schedule) {
        scheduleDatabaseHelper.updateSchedule(schedule);
        loadSchedules();
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleDatabaseHelper.deleteSchedule(schedule);
        loadSchedules();
    }

    private void loadSchedules() {
        schedules.setValue(scheduleDatabaseHelper.getSchedules());
    }
}