package com.example.booking.screens;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class Login extends AbstractScreen{

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/edt_email")
    private AndroidElement emailInput;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/edt_password")
    private AndroidElement passInput;

    @AndroidFindBy(id = "com.cgv.cinema.vn:id/login")
    private AndroidElement submitLogin;


    public void loginToCGVApp(String user, String pass) {
        emailInput.sendKeys(user);
        passInput.sendKeys(pass);
        submitLogin.click();
    }
}
