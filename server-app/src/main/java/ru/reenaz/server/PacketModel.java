package ru.reenaz.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PacketModel {
    private String type;
    @JsonProperty("sensor_info)")
    private SensorInfo sensorInfo;

    public PacketModel() {
    }

    public PacketModel(String type, SensorInfo sensorInfo) {
        this.type = type;
        this.sensorInfo = sensorInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SensorInfo getSensorInfo() {
        return sensorInfo;
    }

    public void setSensorInfo(SensorInfo sensorInfo) {
        this.sensorInfo = sensorInfo;
    }

    @Override
    public String toString() {
        return "PacketModel{" +
            "type='" + type + '\'' +
            ", sensorInfo=" + sensorInfo +
            '}';
    }

    class SensorInfo {
        private boolean isTurnedOn;

        public SensorInfo() {
        }

        public SensorInfo(boolean isTurnedOn) {
            this.isTurnedOn = isTurnedOn;
        }

        public boolean isTurnedOn() {
            return isTurnedOn;
        }

        public void setTurnedOn(boolean turnedOn) {
            isTurnedOn = turnedOn;
        }

        @Override
        public String toString() {
            return "sensorInfo{" +
                "isTurnedOn=" + isTurnedOn +
                '}';
        }
    }
}
