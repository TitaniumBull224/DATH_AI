package com.example.iotai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    TextView txtTem, txtHum, txtLig;
    LabeledSwitch ligBtn, fanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTem = findViewById(R.id.txtTem);
        txtHum = findViewById(R.id.txtHum);
        txtLig = findViewById(R.id.txtLig);
        ligBtn = findViewById(R.id.ligBtn);
        fanBtn = findViewById(R.id.fanBtn);

        ligBtn.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn) sendDataMQTT("nhanchucqt/feeds/den", "1");
                else sendDataMQTT("nhanchucqt/feeds/den", "0");
            }
        });
        fanBtn.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn) sendDataMQTT("nhanchucqt/feeds/quat", "1");
                else sendDataMQTT("nhanchucqt/feeds/quat", "0");
            }
        });

        startMQTT();
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(123234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if(topic.contains("nhietdo"))
                {
                    txtTem.setText(message.toString() + "°C");
                }
                else if(topic.contains("doam"))
                {
                    txtHum.setText(message.toString() + "%");
                }
                else if(topic.contains("anhsang"))
                {
                    txtLig.setText(message.toString() + "LX");
                }
                else if(topic.contains("den"))
                {
                    if(message.toString().equals("1")){ligBtn.setOn(true);}
                    if(message.toString().equals("0")){ligBtn.setOn(false);}
                }
                else if(topic.contains("quat"))
                {
                    if(message.toString().equals("1")){fanBtn.setOn(true);}
                    if(message.toString().equals("0")){fanBtn.setOn(false);}
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}