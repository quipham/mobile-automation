package com.example.booking.devicemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class DeviceAllocationManager {
    private ArrayList<String> devices = new ArrayList<>();
    private ConcurrentHashMap<String, Boolean> deviceMapping = new ConcurrentHashMap<>();
    private static DeviceAllocationManager instance;
    private static DeviceConfiguration androidDevice = new DeviceConfiguration();

    private DeviceAllocationManager() {
        initializeDevices();
    }

    public static DeviceAllocationManager getInstance() {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void initializeDevices() {
        try {
            getAndroidDeviceSerial();
            for (String device : devices) {
                deviceMapping.put(device, true);
            }
            System.out.println(deviceMapping);
        } catch (Exception e) {
            System.out.println("Failed to initialize Devices");
        }
    }

    private void getAndroidDeviceSerial() throws IOException {
        if (androidDevice.getDeviceSerial() != null) {
            System.out.println("Adding Android devices");
            devices.addAll(DeviceConfiguration.deviceSerial);
        }
    }

    public synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, Boolean> devicesMap = deviceMapping.keySet();
        int i = 0;
        for (String device : devicesMap) {
            Thread t = Thread.currentThread();
            t.setName("Thread::" + i);
            System.out.println("Parallel Thread is >>> " + t.getName());
            i++;
            if (deviceMapping.get(device)) {
                deviceMapping.put(device, false);
                System.out.println(device);
                return device;
            }
        }
        System.out.println("Error occurred during adding android devices!");
        return null;
    }
}
