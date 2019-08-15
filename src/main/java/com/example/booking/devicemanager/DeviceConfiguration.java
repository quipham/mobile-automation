package com.example.booking.devicemanager;

import com.example.booking.common.CommandPrompt;

import java.io.IOException;
import java.util.*;


public class DeviceConfiguration {

    private CommandPrompt cmd = new CommandPrompt();
    private Map<String, String> devices = new HashMap<>();
    protected static List<String> deviceSerial = new ArrayList<>();
    protected static List<String> validDeviceIds = new ArrayList<>();
    private static final String DEVICE = "device";

    public void startADB() throws IOException {
        String output = cmd.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines[0].contains("internal or external command")) {
            System.out.println("Please set ANDROID_HOME in your system variables");
        }
    }

    public void stopADB() throws IOException {
        cmd.runCommand("adb kill-server");
    }

    public Map<String, String> getDevices() throws IOException {
        startADB();
        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");
        if (lines.length <= 1) {
            System.out.println("No Android Device Connected >>> ");
            stopADB();
            return null;
        } else {
            for (int i = 1; i < lines.length; i++) {
                lines[i] = lines[i].replaceAll("\\s+", "");

                if (lines[i].contains(DEVICE)) {
                    lines[i] = lines[i].replaceAll(DEVICE, "");
                    String deviceID = lines[i];
                    checkValidDevice(i, deviceID);
                }
            }
            return devices;
        }
    }

    private void checkValidDevice(int intLineNumber, String deviceID) throws IOException {
        if (!validDeviceIds.isEmpty()) {
            if (validDeviceIds.contains(deviceID)) {
                getDeviceInfo(intLineNumber, deviceID);
            }
        } else {
            getDeviceInfo(intLineNumber, deviceID);
        }
    }

    public void getDeviceInfo(int i, String deviceID) throws IOException {
        String strADB = "adb -s ";
        String model = cmd.runCommand(strADB + deviceID + " shell getprop ro.product.model").replaceAll("\\s+", "");
        String brand = cmd.runCommand(strADB + deviceID + " shell getprop ro.product.brand").replaceAll("\\s+", "");
        String osVersion = cmd.runCommand(strADB + deviceID + " shell getprop ro.build.version.release").replaceAll("\\s+", "");
        String deviceName = brand + " " + model;
        String apiLevel = cmd.runCommand(strADB + deviceID + " shell getprop ro.build.version.sdk").replaceAll("\n", "");
        devices.put("deviceID" + i, deviceID);
        devices.put("deviceName" + i, deviceName);
        devices.put("osVersion" + i, osVersion);
        devices.put(deviceID, apiLevel);
        deviceSerial.add(deviceID);
    }

    public List<String> getDeviceSerial() throws IOException {

        startADB();
        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");
        if (lines.length <= 1) {
            System.out.println("No Android Device Connected >>> ");
            return Collections.emptyList();
        }
        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
            if (lines[i].contains(DEVICE)) {
                lines[i] = lines[i].replaceAll(DEVICE, "");
                String deviceID = lines[i];
                addValidDeviceToDeviceSerialList(deviceID);
            }
        }
        return deviceSerial;
    }

    private void addValidDeviceToDeviceSerialList(String deviceID) {
        if (!validDeviceIds.isEmpty()) {
            if (validDeviceIds.contains(deviceID)) {
                deviceSerial.add(deviceID);
                System.out.println("Adding device with user specified: " + deviceID);
            }
        } else {
            System.out.println("Adding all android devices: " + deviceID);
            deviceSerial.add(deviceID);
        }
    }
}
