package com.example.booking.manage;

import com.example.booking.common.Constant;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PoolFactory {
    private static ConcurrentHashMap<String, AndroidDriver<WebElement>> driverPool = new ConcurrentHashMap<>();
    private static Map<String, String> devicePool = new ConcurrentHashMap<>();

    public static synchronized void setDriver(AndroidDriver<WebElement> driver) {
        driverPool.put(Thread.currentThread().getName(), driver);
    }

    public static synchronized AndroidDriver<WebElement> getDriver() {
        return driverPool.get(Thread.currentThread().getName());
    }

    public static synchronized void setDevice(String deviceId) {
        devicePool.put(Thread.currentThread().getName(), deviceId);
    }

    public static synchronized String getDevice() {
        return devicePool.get(Thread.currentThread().getName());
    }

    public static synchronized void closeDriver() {
        driverPool.get(Thread.currentThread().getName()).quit();
    }

    public static synchronized AndroidDriver<WebElement> initLocalDriver(URL localUrl, String deviceId) {
        System.out.println(localUrl + "***debug***");
        System.out.println(deviceId + "***debug***");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceId);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability("appPackage", Constant.APP_SETTING_PACKAGE);
        capabilities.setCapability("appActivity", Constant.APP_SETTING_ACTIVITY);
        return new AndroidDriver<>(localUrl, capabilities);
    }

    private PoolFactory() {
    }
}
