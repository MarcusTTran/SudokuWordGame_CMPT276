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

// UI Test class for the DictionaryFragment Dialog class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class DictionaryFragmentTest {

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

    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
//    @Ignore("Working test")
    @Test
    public void testDictionaryDialogDisplays() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        try {
            dictionaryButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Dictionary button not found");
        }

        UiObject dictionaryDialogPopup = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_DictionaryBox"));
        if (!dictionaryDialogPopup.exists()) {
            fail("Dictionary pop up box did not appear");
        }


        UiObject dictionaryHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryHeader"));
        if (!dictionaryHeader.exists()) {
            fail("Dictionary Header did not appear");
        }

        UiObject dictionaryLang2Table = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang2"));
        if (!dictionaryLang2Table.exists()) {
            fail("Dictionary Language 2 table did not appear in pop up box");
        }

        UiObject dictionaryLang1Table = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang1"));
        if (!dictionaryLang1Table.exists()) {
            fail("Dictionary Language 1 table did not appear in pop up box");
        }

        UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton"));
        if (!dictionaryExitButton.exists()) {
            fail("Dictionary Language exit button did not appear");
        }

        UiObject dictionaryPeeksRemaining = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/peeksRemaining"));
        if (!dictionaryPeeksRemaining.exists()) {
            fail("Dictionary Language peeks remaining did not appear");
        }

    }

    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
//    @Ignore("Working test")
    @Test
    public void testDictionaryDialogDisplaysHorizontal() {
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
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        try {
            dictionaryButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Dictionary button not found");
        }

        UiObject dictionaryDialogPopup = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_DictionaryBox"));
        if (!dictionaryDialogPopup.exists()) {
            fail("Dictionary pop up box did not appear");
        }


        UiObject dictionaryHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryHeader"));
        if (!dictionaryHeader.exists()) {
            fail("Dictionary Header did not appear");
        }

        UiObject dictionaryLang2Table = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang2"));
        if (!dictionaryLang2Table.exists()) {
            fail("Dictionary Language 2 table did not appear in pop up box");
        }

        UiObject dictionaryLang1Table = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang1"));
        if (!dictionaryLang1Table.exists()) {
            fail("Dictionary Language 1 table did not appear in pop up box");
        }

        UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton"));
        if (!dictionaryExitButton.exists()) {
            fail("Dictionary Language exit button did not appear");
        }

        UiObject dictionaryPeeksRemaining = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/peeksRemaining"));
        if (!dictionaryPeeksRemaining.exists()) {
            fail("Dictionary Language peeks remaining did not appear");
        }

    }

    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
//    @Ignore("Working test")
    @Test
    public void testDictionaryDialogPeeksLimit() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));


        UiObject dictionaryDialogExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton"));


        for (int i = 0; i < 7; i++) {
            try  {
                dictionaryButton.click();
                UiObject dictionaryLimitDialogPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryLimit"));
                if (dictionaryLimitDialogPopUp.exists()) {
                    return;
                }
                dictionaryDialogExitButton.click();
            } catch (UiObjectNotFoundException e) {
                fail("Dictionary button or exit button did not appear");
            }
        }
        fail("Limit dialog never appeared");


    }


}



