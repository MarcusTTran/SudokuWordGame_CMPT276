package com.echo.wordsudoku.dialogs;

import static android.os.SystemClock.sleep;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.By;
import android.content.Intent;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// UI Test class for the WarningDialog Dialog class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class WarningDialogTest {

    //Warning: These tests take up to 7 minutes to complete

    int[] puzzleSizes = new int[] {4, 6, 9, 12};

    private UiDevice ourDevice;
    final String INSTRUTEST = "INSTRU_TESTING";

    //If set up exceeds time, will fail test
    private static final int TIMEOUT_TIME = 5000;

    private static final String WORDSUDOKU_PACKAGE = "com.echo.wordsudoku";

    //Test brings us to home page then starts up
    @Before
    public void testSetUp() {
        ourDevice = UiDevice.getInstance(getInstrumentation());
        ourDevice.pressHome();

        final String appLauncher = ourDevice.getLauncherPackageName();
        if (appLauncher.equals(null)) {
            //If app launcher does not exist fail the test
            fail("App launcher does not exist");
        }

        ourDevice.wait(Until.hasObject(By.pkg(appLauncher).depth(0)), TIMEOUT_TIME);
        //Get the context to launch our app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(WORDSUDOKU_PACKAGE);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Start the app up
        context.startActivity(appIntent);
        //Wait until device has app
        ourDevice.wait(Until.hasObject(By.pkg(WORDSUDOKU_PACKAGE).depth(0)), TIMEOUT_TIME);
    }


    //Test that all elements are correctly displayed in the WarningDialog
//    @Ignore("Working test")
    @Test
    public void testWarningDialogDisplay() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        if (!okButton.exists()) {
            fail("Ok button was not displayed");
        }

        UiObject warningHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningHeader"));
        if (!warningHeader.exists()) {
            fail("WarningHeader was not displayed");
        }

        UiObject warningBody = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningInformation"));
        if (!warningBody.exists()) {
            fail("WarningInformation was not displayed");
        }

    }

    //Test that all elements are correctly displayed in the WarningDialog on horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testWarningDialogDisplayHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        if (!okButton.exists()) {
            fail("Ok button was not displayed");
        }

        UiObject warningHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningHeader"));
        if (!warningHeader.exists()) {
            fail("WarningHeader was not displayed");
        }

        UiObject warningBody = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningInformation"));
        if (!warningBody.exists()) {
            fail("WarningInformation was not displayed");
        }

    }



    //Test that clicking the OK button on WarningDialog correctly closes the dialog
//    @Ignore("Working test")
    @Test
    public void testWarningDialogOkButton() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        try {
            okButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Ok Button was not displayed");
        }

        if (warningPopUp.exists()) {
            fail("Warning pop up was not exited on OK button click");
        }

    }

    //Test that clicking the OK button on WarningDialog correctly closes the dialog on horizontal landscape
//    @Ignore("Working test")
    @Test
    public void testWarningDialogOkButtonHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        try {
            okButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Ok Button was not displayed");
        }

        if (warningPopUp.exists()) {
            fail("Warning pop up was not exited on OK button click");
        }

    }


    //Test that pressing the back button on WarningDialog correctly closes the dialog
//    @Ignore("Working test")
    @Test
    public void testWarningDialogBackButton() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        ourDevice.pressBack();

        if (warningPopUp.exists()) {
            fail("Warning pop up was not exited on OK button click");
        }

    }


}


