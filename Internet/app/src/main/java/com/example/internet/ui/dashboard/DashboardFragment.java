package com.example.internet.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.internet.databinding.FragmentDashboardBinding;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

// Add the necessary imports for MQTT
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MqttAndroidClient mqttAndroidClient;
    public final String[] arrayFeeds = { "/feeds/nhietdo", "/feeds/doam", "/feeds/anhsang", "/feeds/den", "/feeds/quat" };
    private final String serverUri = "tcp://io.adafruit.com:1883";
    private final String clientId = "95468879";
    private final String username = "nhanchucqt";
    private final String password = "aio_fhFH288kPEAxVDqKBP3cfppuVMTI";
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        dashboardViewModel.getTem().observe(getViewLifecycleOwner(), value -> binding.txtTem.setText(value));

        dashboardViewModel.getHum().observe(getViewLifecycleOwner(), value -> binding.txtHum.setText(value));

        dashboardViewModel.getLig().observe(getViewLifecycleOwner(), value -> binding.txtLig.setText(value));

        // Set up toggle switches
        LabeledSwitch lightSwitch = binding.ligBtn;
        lightSwitch.setOnToggledListener((toggleableView, isOn) -> {
            String payload = isOn ? "1" : "0";
            try {
                mqttAndroidClient.publish(username + arrayFeeds[3], new MqttMessage(payload.getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });

        LabeledSwitch fanSwitch = binding.fanBtn;
        fanSwitch.setOnToggledListener((toggleableView, isOn) -> {
            String payload = isOn ? "1" : "0";
            try {
                mqttAndroidClient.publish(username + arrayFeeds[4], new MqttMessage(payload.getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });

        // Set up MQTT connection
        mqttAndroidClient = new MqttAndroidClient(getContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                // Subscribe to topics
                try {
                    mqttAndroidClient.subscribe(username + arrayFeeds[0], 0, (topic, message) -> dashboardViewModel.setTem(new String(message.getPayload())));
                    mqttAndroidClient.subscribe(username + arrayFeeds[1], 0, (topic, message) -> dashboardViewModel.setHum(new String(message.getPayload())));
                    mqttAndroidClient.subscribe(username + arrayFeeds[2], 0, (topic, message) -> dashboardViewModel.setLig(new String(message.getPayload())));
                    mqttAndroidClient.subscribe(username + arrayFeeds[3], 0, (topic, message) -> {
                        String payload = new String(message.getPayload());
                        boolean isOn = payload.equals("1");
                        lightSwitch.setOn(isOn);
                    });
                    mqttAndroidClient.subscribe(username + arrayFeeds[4], 0, (topic, message) -> {
                        String payload = new String(message.getPayload());
                        boolean isOn = payload.equals("1");
                        fanSwitch.setOn(isOn);
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.e("MQTT", "Connection to Adafruit server lost", cause);
                try {
                    mqttAndroidClient.connect(mqttConnectOptions);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {}

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });

        try {
            mqttAndroidClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            Log.e("MQTT", "Error connecting to Adafruit server", e);
        }



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}