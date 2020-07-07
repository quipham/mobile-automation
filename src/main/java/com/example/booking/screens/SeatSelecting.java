package com.example.booking.screens;

import com.example.booking.model.BillingInfo;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.NoSuchElementException;

import static org.testng.Assert.assertEquals;

public class SeatSelecting extends AbstractScreen {

    @AndroidFindBy(xpath = "//*[contains(@text,'VIP')]")
    private AndroidElement vipSeat;

    @AndroidFindBy(xpath = "//*[contains(@text,'Thường')]")
    private AndroidElement normalSeat;

    @AndroidFindBy(xpath = "//*[contains(@text,'amour')]")
    private AndroidElement lAmourSeat;

    @AndroidFindBy(xpath = "//*[contains(@text,'Ghế đôi')]")
    private AndroidElement doubleSeat;

    @AndroidFindBy(xpath = "//*[contains(@text,'Deluxe')]")
    private AndroidElement deluxeSeat;

    @AndroidFindBy(xpath = "//*[contains(@text,'Premium')]")
    private AndroidElement premiumSeat;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/movie_name")
    private AndroidElement movieName;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/movie_type")
    private AndroidElement movieType;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/price")
    private AndroidElement totalPrice;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/buy_now")
    private AndroidElement buyNowBtn;

    @AndroidFindBy(id = "android:id/button1")
    private AndroidElement allowBtn;

    private BillingInfo billingInfo;


    public void tryToSelectAvailableSeat() {
        normalSeat.click();
        lAmourSeat.click();
        doubleSeat.click();
        billingInfo = new BillingInfo();
        billingInfo.setMovieName(movieName.getText());
        billingInfo.setDescription(movieType.getText());
        billingInfo.setTotalAmount(totalPrice.getText());
        buyNowBtn.click();
    }

    public void confirmTnCIfNeeded() {
        try {
            allowBtn.click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void verifyBillingInformation() {
        assertEquals(billingInfo.getMovieName(), movieName.getText());
        assertEquals(billingInfo.getDescription(), movieType.getText());
        assertEquals(billingInfo.getTotalAmount(), totalPrice.getText());
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }
}
