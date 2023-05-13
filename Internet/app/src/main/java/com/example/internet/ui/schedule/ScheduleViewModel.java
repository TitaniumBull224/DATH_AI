package com.example.internet.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

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

    public LiveData<List<Schedule>> getSchedulesByDate(String date) {
        MutableLiveData<List<Schedule>> filteredSchedules = new MutableLiveData<>();
        new Thread(() -> {
            List<Schedule> schedulesFromDB = scheduleDatabaseHelper.getSchedules();
            List<Schedule> filteredList = schedulesFromDB.stream()
                    .filter(schedule -> schedule.getDateBegin().compareTo(date) <= 0 && schedule.getDateEnd().compareTo(date) >= 0)
                    .collect(Collectors.toList());
            filteredSchedules.postValue(filteredList);
        }).start();
        return filteredSchedules;
    }

}