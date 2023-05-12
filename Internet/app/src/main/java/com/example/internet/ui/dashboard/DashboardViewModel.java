package com.example.internet.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.internet.MQTTHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<String> temperature = new MutableLiveData<>();
    private final MutableLiveData<String> humidity = new MutableLiveData<>();
    private final MutableLiveData<String> light = new MutableLiveData<>();
    //private final MutableLiveData<String> lightBtn = new MutableLiveData<>();
    //private final MutableLiveData<String> fanBtn = new MutableLiveData<>();

    MQTTHelper mqttHelper;


    public LiveData<String> getTem() { return temperature; }
    public LiveData<String> getHum() { return humidity; }
    public LiveData<String> getLig() { return light; }
    //public LiveData<String> getLigBtn() { return lightBtn; }
    //public LiveData<String> getFanBtn() { return fanBtn; }

    public void setTem(String value) { temperature.setValue(value); }
    public void setHum(String value) { humidity.setValue(value); }
    public void setLig(String value) { light.setValue(value); }

    //public void setLightBtn(String lB) { lightBtn.setValue(lB); }
    //public void setFanBtn(String fB) { fanBtn.setValue(fB); }

}