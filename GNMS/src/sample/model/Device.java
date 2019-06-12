package sample.model;

public class Device {
    private int id = -1;
    private String deviceType;

    public Device(String deviceType) {
        this.deviceType = deviceType;
    }

    public Device(int id, String deviceType) {
        this.id = id;
        this.deviceType = deviceType;
    }

    public int getDeviceId() {
        return id;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
