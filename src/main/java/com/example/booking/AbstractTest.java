package com.example.booking;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.example.booking.common.AndroidControl;
import com.example.booking.common.Constant;
import com.example.booking.devicemanager.DeviceAllocationManager;
import com.example.booking.manage.ExtentManager;
import com.example.booking.manage.PoolFactory;
import com.example.booking.screens.CGVHome;
import com.example.booking.screens.Login;
import com.example.booking.screens.SeatSelecting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


public abstract class AbstractTest {
    private AndroidControl control;
    protected ExtentTest extentTest;

    protected CGVHome cgvHome;
    protected Login login;
    protected SeatSelecting seatSelecting;

    protected static final String INVALID_PIN = "333333";

    @AfterSuite
    public void globalTearDown() {
        PoolFactory.closeDriver();
    }

    @BeforeClass
    public void setUp() {
        String deviceId = DeviceAllocationManager.getInstance().getNextAvailableDeviceId();
        PoolFactory.setDevice(deviceId);
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
        service.start();

        control = new AndroidControl(deviceId);
        if (control.isAPKInstalled(Constant.APP_CGV_PACKAGE)) {
            String apkPath = System.getProperty("user.dir") + "/src/main/resources/apk/CGV.apk";
            control.installAPK(apkPath);
        }

        clearAppData();
        PoolFactory.setDriver(PoolFactory.initLocalDriver(service.getUrl(), deviceId));
        AndroidDriver<WebElement> driver = PoolFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(14000, TimeUnit.MILLISECONDS);
        initScreenObject();
    }

    @BeforeMethod
    public void startApp(Method method) throws InterruptedException {
        ExtentReports extent = ExtentManager.getInstance();
        extentTest = extent.createTest(method.getName());
        ExtentManager.setTest(extentTest);
        control.openCGVApp();
        Thread.sleep(5000);
    }

    @AfterMethod
    public void closeApp() {
        forceCloseApp();
    }

    private void forceCloseApp() {
        control.forceCloseCGVApp();
    }

    private void clearAppData() {
        control.clearCGVAppData();
    }

    private void initScreenObject() {
        cgvHome = new CGVHome();
        login = new Login();
        seatSelecting = new SeatSelecting();
    }

}
