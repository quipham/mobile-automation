package com.example.booking.screens;

import com.example.booking.common.AndroidControl;
import com.example.booking.manage.PoolFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

abstract class AbstractScreen {
    AndroidDriver driver;
    AndroidControl control;
    int width;
    int height;

    AbstractScreen() {
        driver = PoolFactory.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        control = new AndroidControl(PoolFactory.getDevice());
        Dimension dimension = driver.manage().window().getSize();
        width = dimension.getWidth();
        height = dimension.getHeight();
    }

//    @AndroidFindBy(xpath = "//*[@text= 'CHO PHÉP' and @package='com.google.android.packageinstaller']")
//    protected AndroidElement allowBtn;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    protected AndroidElement allowBtn;

    @AndroidFindBy(xpath = "//*[@text='Tiếp tục']")
    protected AndroidElement continueBtn;

    @AndroidFindBy(xpath = "//*[@text='Tiếp']")
    protected AndroidElement nextBtn;

    @AndroidFindBy(xpath = "//*[@text='OK']")
    private AndroidElement okBtn;

    @AndroidFindBy(id = "com.vn.app.bagang:id/id_docked_tap_icon")
    private List<AndroidElement> slideMenu;

    public void clickAllowPermissionBtn() {
        allowBtn.click();
    }

    public void clickContinueBtn() {
        continueBtn.click();
    }

    public void clickNextBtn() {
        nextBtn.click();
    }

    public void clickOkBtn() {
        okBtn.click();
    }

    void waitForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    protected void setDate() {
        okBtn.click();
    }

    protected AndroidElement findElementByText(String text) {
        return (AndroidElement) driver.findElement(By.xpath("//*[@text='" + text + "']"));
    }

    public void clickMoreTab() {
        slideMenu.get(4).click();
    }
}
