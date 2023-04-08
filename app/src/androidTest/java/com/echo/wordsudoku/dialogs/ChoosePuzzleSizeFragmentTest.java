package com.echo.wordsudoku.dialogs;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.By;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class ChoosePuzzleSizeFragmentTest {

    //Warning: These tests take up to 10 minutes to complete

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




    //Test that the cancel button on the Custom sized puzzle selection dialog cancels the dialog
//    @Ignore("Working test")
    @Test
    public void testCustomSizedDialogDisplays() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Click custom size button
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject choosepuzzleSizeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/customPuzzleSizeSelect"));
        if (!choosepuzzleSizeFragment.exists()) {
            fail("Choose Puzzle Size Dialog Fragment not displayed");
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button"));
        if (!doneButton.exists()) {
            fail("Choose Puzzle Size Dialog Fragment Done button not displayed");
        }

        UiObject cancelButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/cancel_button"));
        if (!cancelButton.exists()) {
            fail("Choose Puzzle Size Dialog Fragment Cancel button not displayed");
        }

        UiObject puzzleSize4x4Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button"));
        if (!puzzleSize4x4Button.exists()) {
            fail("Puzzle size 4x4 button was not displayed");
        }

        UiObject puzzleSize6x6Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_6x6_button"));
        if (!puzzleSize6x6Button.exists()) {
            fail("Puzzle size 6x6 button was not displayed");
        }

        UiObject puzzleSize9x9Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_9x9_puzzle"));
        if (!puzzleSize9x9Button.exists()) {
            fail("Puzzle size 9x9 button was not displayed");
        }

        UiObject puzzleSize12x12Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_12x12_puzzle"));
        if (!puzzleSize12x12Button.exists()) {
            fail("Puzzle size 12x12 button was not displayed");
        }


    }

    //Test that the cancel button on the Custom sized puzzle selection dialog cancels the dialog
//    @Ignore("Working test")
    @Test
    public void testCustomSizedDialogDisplaysHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Click custom size button
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject choosepuzzleSizeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/customPuzzleSizeSelect"));
        if (!choosepuzzleSizeFragment.exists()) {
            fail("Choose Puzzle Size Dialog Fragment not displayed");
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button"));
        if (!doneButton.exists()) {
            fail("Choose Puzzle Size Dialog Fragment Done button not displayed");
        }

        UiObject cancelButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/cancel_button"));
        if (!cancelButton.exists()) {
            fail("Choose Puzzle Size Dialog Fragment Cancel button not displayed");
        }

        UiObject puzzleSize4x4Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button"));
        if (!puzzleSize4x4Button.exists()) {
            fail("Puzzle size 4x4 button was not displayed");
        }

        UiObject puzzleSize6x6Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_6x6_button"));
        if (!puzzleSize6x6Button.exists()) {
            fail("Puzzle size 6x6 button was not displayed");
        }

        UiObject puzzleSize9x9Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_9x9_puzzle"));
        if (!puzzleSize9x9Button.exists()) {
            fail("Puzzle size 9x9 button was not displayed");
        }

        UiObject puzzleSize12x12Button = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_12x12_puzzle"));
        if (!puzzleSize12x12Button.exists()) {
            fail("Puzzle size 12x12 button was not displayed");
        }


    }

    //Test that the cancel button on the Custom sized puzzle selection dialog cancels the dialog
//    @Ignore("Working test")
    @Test
    public void testCustomSizedDialogDoneButton() {
//Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Click custom size button
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        //Click cancel button in dialog
        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose Puzzle Size Dialog Fragment Done button not displayed");
        }

        UiObject puzzleFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment"));
        if (!puzzleFragment.exists()) {
            fail("Puzzle Fragment was not displayed after clicking Done");
        }


    }





    //Test that the cancel button on the Custom sized puzzle selection dialog cancels the dialog
//    @Ignore("Working test")
    @Test
    public void testCustomSizedDialogCancelButton() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Click custom size button
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        //Click cancel button in dialog
        UiObject customSizedCancelButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/cancel_button").className("android.widget.Button"));
        try {
            customSizedCancelButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle cancel button not found");
        }

        UiObject choosePuzzleModeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choosePuzzleModeFragment").className("android.view.ViewGroup"));
        if (!choosePuzzleModeFragment.exists()) {
            fail("Choose Puzzle Mode Fragment was not displayed");
        }

    }




}


