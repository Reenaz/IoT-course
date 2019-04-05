package ru.reenaz.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.exception.InvalidPinException;
import com.pi4j.wiringpi.Gpio;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Starter {

    //2000 mls
    private static final Pin LASER_PIN = RaspiPin.GPIO_00;
    private static byte laserCurrentState;
    private static MqttClient mqttClient = null;
    private static String mqttHost = "127.0.0.1";
    private static Integer mqttPort = 1883;
    private static String topicName = "laser_info";
    private String clientId = "test";

    public static void main(String[] args) {
        try {
            System.out.println("App started");
            if (Gpio.wiringPiSetup() == -1) throw new InvalidPinException(LASER_PIN);
            while(true) try{
                //laserCurrentState = (byte) Gpio.digitalRead(LASER_PIN.getAddress());
                Gpio.digitalWrite(LASER_PIN.getAddress(), laserCurrentState);
                Thread.sleep(50);
            } catch (Exception e) {
                    e.printStackTrace();
                    break;

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void start() {
        String protocol = "tcp://";
        String brokerUrl = protocol + this.mqttHost + ":" + this.mqttPort;

        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setCleanSession(true);

        try {
            mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            mqttClient.connect(connectionOptions);
            subscribeToTopic(topicName);
            System.out.println(String.format("Connect to : %s - %s", brokerUrl, clientId));

        } catch (MqttException me) {
            System.err.println(String.format("MqttException - onConfig:%s", me.getLocalizedMessage()));
        }
    }

    private static void subscribeToTopic(String topicName) {
        try {
            mqttClient.subscribe(topicName, (topic, msg) -> {
                ObjectMapper objectMapper = new ObjectMapper();
                PacketModel packet = objectMapper.readValue(msg.toString(), PacketModel.class);
                System.out.println(packet);
                laserCurrentState = (byte) (packet.getSensorInfo().isTurnedOn() ? 1 : 0);

            });

        } catch (MqttException me) {
            me.printStackTrace();
        }
    }
}
