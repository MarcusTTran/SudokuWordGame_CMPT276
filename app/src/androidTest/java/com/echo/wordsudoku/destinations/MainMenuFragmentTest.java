package com.echo.wordsudoku.destinations;

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

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class MainMenuFragmentTest {

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

    //Test that all options buttons are correctly displayed on the main menu
//    @Ignore("Working test")
    @Test
    public void testMainMenuDisplays() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button"));
        if (!newGameButton.exists()) {
            fail("New game button does not appear");
        }

        UiObject loadGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/load_game_button"));
        if (!loadGameButton.exists()) {
            fail("Load game button does not appear");
        }

        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        if (!puzzleLanguageButton.exists()) {
            fail("Change language button does not appear");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        if (!exitButton.exists()) {
            fail("Exit button does not appear");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        if (!customWordsButton.exists()) {
            fail("Custom words button does not appear");
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        if (!settingsButton.exists()) {
            fail("Settings button does not appear");
        }

    }

    //Test that all main menu buttons are correctly displayed in a horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testMainMenuDisplaysHorizontal() {
        try {
            ourDevice.setOrientationLeft();
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button"));
        if (!newGameButton.exists()) {
            fail("New game button does not appear");
        }

        UiObject loadGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/load_game_button"));
        if (!loadGameButton.exists()) {
            fail("Load game button does not appear");
        }

        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        if (!puzzleLanguageButton.exists()) {
            fail("Change language button does not appear");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        if (!exitButton.exists()) {
            fail("Exit button does not appear");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        if (!customWordsButton.exists()) {
            fail("Custom words button does not appear");
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        if (!settingsButton.exists()) {
            fail("Settings button does not appear");
        }

    }


    //Test that clicking the Settings button in the main menu correctly displays the Settings fragment
//    @Ignore("Working test")
    @Test
    public void testMainMenuSettingsButtonNavigation() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        UiObject settingsActionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar_container"));
        if (!settingsActionBar.exists()) {
            fail("Settings action bar was not displayed");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar"));
        try {
            actionBar.getChild(new UiSelector().className("android.widget.ImageButton"));
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        UiObject settingsRecyclerFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        if (!settingsRecyclerFragment.exists()) {
            fail("Settings page was not displayed");
        }

    }

    //Test that Clicking the Choose custom words button takes us to the Choose custom words fragment
//    @Ignore("Working test")
    @Test
    public void testMainMenuChooseCustomWordsButtonNavigation() {
        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        try {
            customWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Words Button not found");
        }

        UiObject chooseCustomWords = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment").className("android.view.ViewGroup"));
        if (!chooseCustomWords.exists()) {
            fail("Choose custom words fragment was not displayed");
        }
    }

    //Test that clicking the new game button takes you to the Choose puzzle mode fragment
    //    @Ignore("Working test")
    @Test
    public void testMainMenuNewGameButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not found");
        }

        UiObject choosePuzzleModeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choosePuzzleModeFragment").className("android.view.ViewGroup"));
        if (!choosePuzzleModeFragment.exists()) {
            fail("Choose Puzzle Mode was not displayed");
        }
    }


    //Test clicking the Exit button correctly exits the app
//    @Ignore("Working test")
    @Test
    public void testMainMenuExitButton() {
        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        try {
            exitButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Exit Button not found");
        }

        UiObject mainMenu = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/main_menu_fragment"));
        if (mainMenu.exists()) {
            fail("Exit button did not work");
        }
    }



    //Test that when users click the puzzle language button on the main menu, the button text displays the
    //language it has been set to
//    @Ignore("Working test")
    @Test
    public void testMainMenuPuzzleLanguageButtonUpdate() {
        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        String s = "";
        try {
            s = puzzleLanguageButton.getText();
            puzzleLanguageButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle language button not found");
        }

        UiObject puzzleLanguageUpdateText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        try {
            if (s.contains("French")) {
                if (!puzzleLanguageUpdateText.getText().equals("Puzzle Language : English")) {
                    fail("Puzzle Language Button did not display set language correctly");
                }
            } else {
                if (!puzzleLanguageUpdateText.getText().equals("Puzzle Language : French")) {
                    fail("Puzzle Language Button did not display set language correctly");
                }
            }

        } catch (UiObjectNotFoundException e) {
            fail("Puzzle Language Button text did not update on click");
        }

    }






}

