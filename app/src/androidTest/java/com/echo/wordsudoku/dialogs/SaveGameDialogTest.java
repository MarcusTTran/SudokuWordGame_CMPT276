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

// UI Test class for the SaveGameDialog Dialog class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class SaveGameDialogTest {

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

    //Test that the save dialog correctly appears
//    @Ignore("Working test")
    @Test
    public void testSaveDialogDisplays() {
        //For all 3 button options CANCEL, YES, NO
        int totalButtons = 3;

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog box is displayed
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog does not appear");
        }

        //Check that the save dialog message is displayed in the box
        UiObject saveDialogMessage = ourDevice.findObject(new UiSelector().resourceId("android:id/message").className("android.widget.TextView"));
        if (!saveDialogMessage.exists()) {
            fail("SaveDialog message did not appear");
        }

        //Check that all buttons displayed in the save game dialog box
        for (int i = 0; i < totalButtons; i++) {
            UiObject button = ourDevice.findObject(new UiSelector().resourceId("android:id/button"+ (i + 1)));
            if (!button.exists()) {
                fail("All buttons in save dialog were not displayed");
            }
        }

    }

    //Test that the save dialog correctly appears correctly in horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testSaveDialogDisplaysHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        //For all 3 button options CANCEL, YES, NO
        int totalButtons = 3;

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog box is displayed
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog does not appear");
        }

        //Check that the save dialog message is displayed in the box
        UiObject saveDialogMessage = ourDevice.findObject(new UiSelector().resourceId("android:id/message").className("android.widget.TextView"));
        if (!saveDialogMessage.exists()) {
            fail("SaveDialog message did not appear");
        }

        //Check that all buttons displayed in the save game dialog box
        for (int i = 0; i < totalButtons; i++) {
            UiObject button = ourDevice.findObject(new UiSelector().resourceId("android:id/button"+ (i + 1)));
            if (!button.exists()) {
                fail("All buttons in save dialog were not displayed");
            }
        }

    }

    //Test that the cancel button on the save dialog correctly closes the dialog and returns user to
    // puzzle fragment page
//    @Ignore("Working test")
    @Test
    public void testSaveDialogCancel() {

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog box is displayed
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog does not appear");
        }

        UiObject saveDialogCancel = ourDevice.findObject(new UiSelector().resourceId("android:id/button3").className("android.widget.Button"));
        try {
            saveDialogCancel.click();
        } catch (UiObjectNotFoundException e) {
            fail("Save dialog cancel button did not appear");
        }

        UiObject puzzleFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment"));
        if (!puzzleFragment.exists()) {
            fail("Save dialog did not correctly cancel to puzzle fragment page");
        }

    }


    //Test that the No button on the save dialog correctly returns user to the main menu
//    @Ignore("Working test")
    @Test
    public void testSaveDialogNo() {

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog box is displayed
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog does not appear");
        }

        UiObject saveDialogNo = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            saveDialogNo.click();
        } catch (UiObjectNotFoundException e) {
            fail("Save dialog No button did not appear");
        }

        UiObject mainMenuFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen"));
        if (!mainMenuFragment.exists()) {
            fail("Save dialog did not correctly exit to main menu page");
        }



    }


    //Test that the Yes button on the save dialog correctly returns user to the main menu
//    @Ignore("Working test")
    @Test
    public void testSaveDialogYes() {

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog box is displayed
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog does not appear");
        }

        UiObject saveDialogYes = ourDevice.findObject(new UiSelector().resourceId("android:id/button1").className("android.widget.Button"));
        try {
            saveDialogYes.click();
        } catch (UiObjectNotFoundException e) {
            fail("Save dialog Yes button did not appear");
        }

        UiObject mainMenuFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen"));
        if (!mainMenuFragment.exists()) {
            fail("Save dialog did not correctly exit to main menu page");
        }



    }





}


