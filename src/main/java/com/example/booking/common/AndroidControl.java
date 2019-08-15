package com.example.booking.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AndroidControl {
    public AndroidControl(String deviceId) {
        this.deviceId = deviceId;
        adbs = "adb -s " + deviceId + " shell ";
    }

    private String deviceId;

    private static final Logger logger = LogManager.getLogger(AndroidControl.class);

    private String adbs;

    public void touchByBound(String bound) {
        String[] coordinate = bound.split("([\\]\\[\\,])");
        String x = coordinate[1];
        String y = coordinate[2];
        executeCommand(adbs + String.format("input tap %s %s", x, y));
    }

    public void swipe(int startX, int startY, int endX, int endY) {
        executeCommand(adbs + String.format("input touchscreen swipe %d %d %d %d", startX, startY, endX, endY));
    }

    public void softSwipeToTop() {
        swipe(180, 280, 0, 0);
    }

    public void swipeToTop(int startX, int startY) {
        swipe(startX, startY, 0, 0);
    }

    private void inputKeyEvent(String keyCode) {
        executeCommand(adbs + String.format("input keyevent %s", keyCode));
    }

    public void inputNumberByKeyEvent(String number) {
        for (String s : keyCodeMappingByNumber(number))
            inputKeyEvent(s);
    }

    public boolean isAPKInstalled(String packageName) {
        return executeCommand(adbs + "pm list packages " + packageName).isEmpty();
    }

    public void installAPK(String apkPath) {
        executeCommand("adb -s " + deviceId + " install " + apkPath);
    }

    public void openCGVApp() {
        executeCommand(adbs + "monkey -p "
                + Constant.APP_CGV_PACKAGE
                + " -c android.intent.category.LAUNCHER 1");
    }

    public void forceCloseCGVApp() {
        executeCommand(adbs + "am force-stop " + Constant.APP_CGV_PACKAGE);
    }

    public void clearCGVAppData() {
        executeCommand(adbs + "pm clear " + Constant.APP_CGV_PACKAGE);
    }

    public void pressBackKey() {
        inputKeyEvent("4");
    }

    public String getCurrentOTP() {
        String output = executeCommand(adbs + "dumpsys notification");
        return output.split("TunaiKita Verification Code: ")[1].substring(0, 4);
    }

    private String[] keyCodeMappingByNumber(String number) {
        String[] result = new String[20];
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            switch (String.valueOf(c)) {
                case "0":
                    result[i] = "KEYCODE_0";
                    break;
                case "1":
                    result[i] = "KEYCODE_1";
                    break;
                case "2":
                    result[i] = "KEYCODE_2";
                    break;
                case "3":
                    result[i] = "KEYCODE_3";
                    break;
                case "4":
                    result[i] = "KEYCODE_4";
                    break;
                case "5":
                    result[i] = "KEYCODE_5";
                    break;
                case "6":
                    result[i] = "KEYCODE_6";
                    break;
                case "7":
                    result[i] = "KEYCODE_7";
                    break;
                case "8":
                    result[i] = "KEYCODE_8";
                    break;
                case "9":
                    result[i] = "KEYCODE_9";
                    break;
                default:
                    result[i] = "Invalid number";
                    break;
            }
        }
        return result;
    }

    public static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();

        Process p;

        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (Exception e) {
            logger.error(e);
        }

        return output.toString();
    }
}
