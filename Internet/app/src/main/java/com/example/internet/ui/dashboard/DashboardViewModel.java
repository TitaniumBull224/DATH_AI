package com.example.internet.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<String> temperature = new MutableLiveData<>();
    private final MutableLiveData<String> humidity = new MutableLiveData<>();
    private final MutableLiveData<String> light = new MutableLiveData<>();
    //private final MutableLiveData<String> lightBtn = new MutableLiveData<>();
    //private final MutableLiveData<String> fanBtn = new MutableLiveData<>();
    public LiveData<String> getTem() { return temperature; }
    public LiveData<String> getHum() { return humidity; }
    public LiveData<String> getLig() { return light; }
    //public LiveData<String> getLigBtn() { return lightBtn; }
    //public LiveData<String> getFanBtn() { return fanBtn; }

    public void setTem(String value) { temperature.postValue(value + "°C"); }
    public void setHum(String value) { humidity.postValue(value + "%"); }
    public void setLig(String value) { light.postValue(value + "LX"); }

    //public void setLightBtn(String lB) { lightBtn.setValue(lB); }
    //public void setFanBtn(String fB) { fanBtn.setValue(fB); }

}