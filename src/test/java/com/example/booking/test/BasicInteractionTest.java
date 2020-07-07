package com.example.booking.test;

import com.example.booking.AbstractTest;
import org.testng.annotations.Test;

public class BasicInteractionTest extends AbstractTest {
    @Test
    public void orderTicket() {
        extentTest.info("Start test with User: " + USER_NAME);
        cgvHome.selectShowingMovie();
        extentTest.info("Selecting showing movie on Home screen");
        cgvHome.clickOnBookTicketBtn();
        cgvHome.selectTheFirstCinemaAndTime();
        extentTest.info("Selecting The first cinema and showtime");
        seatSelecting.tryToSelectAvailableSeat();
        extentTest.info("Try to select available seat");
        seatSelecting.confirmTnCIfNeeded();
        seatSelecting.verifyBillingInformation();
        extentTest.info("Verify Billing Information:");
        extentTest.info("Movie Name: " + seatSelecting.getBillingInfo().getMovieName());
        extentTest.info("Type: " + seatSelecting.getBillingInfo().getDescription());
        extentTest.info("Total Amount to pay: " + seatSelecting.getBillingInfo().getTotalAmount());
    }
}

