package com.example.booking.screens;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

public class CGVHome extends AbstractScreen {

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/image")
    private List<AndroidElement> movieList;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/btn_right_menu")
    private AndroidElement menuBarBtn;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/user_name")
    private AndroidElement loginBtn;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/book_now")
    private AndroidElement bookNowBtn;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/show_time")
    private List<AndroidElement> showTimeList;


    public void openMenuBar() {
        menuBarBtn.click();
    }

    public void clickOnLoginBtn() {
        loginBtn.click();
    }

    public void selectShowingMovie() {
        movieList.get(2).click();
    }

    public void clickOnBookTicketBtn() {
        bookNowBtn.click();
    }

    public void selectTheFirstCinemaAndTime() {
        showTimeList.get(0).click();
    }
}
