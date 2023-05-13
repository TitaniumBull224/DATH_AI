package com.example.internet.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Schedule>> schedules;
    private final ScheduleDatabaseHelper scheduleDatabaseHelper;


    public ScheduleViewModel(Application application) {
        super(application);
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
        new Thread(() -> {
            List<Schedule> schedulesFromDB = scheduleDatabaseHelper.getSchedules();
            schedules.postValue(schedulesFromDB);
        }).start();
    }

}