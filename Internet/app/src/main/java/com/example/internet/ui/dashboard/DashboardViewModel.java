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
    private final MutableLiveData<String> temperature, humidity, light;
    private final MutableLiveData<String> ligBtn, fanBtn;
    MQTTHelper mqttHelper;


    public DashboardViewModel() {
        temperature = new MutableLiveData<>("");
        humidity  = new MutableLiveData<>("");
        light = new MutableLiveData<>("");

        ligBtn = new MutableLiveData<>("0");
        fanBtn = new MutableLiveData<>("0");
    }

    public LiveData<String> getTem() { return temperature;}
    public LiveData<String> getHum() { return humidity;}
    public LiveData<String> getLig() { return light;}
    //public LiveData<String> getLigBtn() { return ligBtn;}
    //public LiveData<String> getFanBtn() { return fanBtn;}

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(123234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(StandardCharsets.UTF_8);
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        } catch (MqttException e) {

        }
    }

    public void startMQTT(Context context){
        mqttHelper = new MQTTHelper(context);
        mqttHelper.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message);
                if (topic.contains("nhietdo")) {
                    temperature.setValue(message + "°C");
                }
                else if (topic.contains("doam"))  {
                    humidity.setValue(message + "%");
                }
                else if (topic.contains("anhsang")) {
                    light.setValue(message + "LX");
                }
                else if (topic.contains("den")) {
                    if(message.toString().equals("1")) {ligBtn.setValue("1");}
                    if(message.toString().equals("0")) {ligBtn.setValue("0");}
                }
                else if (topic.contains("quat")) {
                    if(message.toString().equals("1")) {fanBtn.setValue("1");}
                    if(message.toString().equals("0")) {fanBtn.setValue("0");}
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

        });
    }
    /*public void clear() {
        temperature.setValue("");
        humidity.setValue("");
        light.setValue("");
        ligBtn.setValue("");
        fanBtn.setValue("");
    }*/

}