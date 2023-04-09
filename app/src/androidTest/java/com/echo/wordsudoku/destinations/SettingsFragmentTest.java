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

import java.util.Set;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// UI Test class for the SettingsFragment class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

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




    //Test that clicking the timer switch in the settings correctly updates the subtext below Timer Puzzle
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsTimerSwitch() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }
        UiObject settingsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        try {
            UiObject timerLayout = settingsFragment.getChild(new UiSelector().index(1));
            UiObject timerLayout1 = timerLayout.getChild(new UiSelector().index(1));
            UiObject switchWidget = timerLayout1.getChild(new UiSelector().resourceId("com.echo.wordsudoku:id/switchWidget"));
            switchWidget.click();
            if (switchWidget.isChecked()) {
                UiObject timerDisplay = ourDevice.findObject(new UiSelector().textContains("Timer is on"));
                if (!timerDisplay.exists()) {
                    fail("Timer display did not correctly update");
                }
                switchWidget.click();
            } else {
                UiObject timerDisplay = ourDevice.findObject(new UiSelector().textContains("Timer is off"));
                if (!timerDisplay.exists()) {
                    fail("Timer display did not correctly update");
                }
            }

        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }


    }


    //Test that all setting headers and switches are displayed in horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testSettingsHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings button not found");
        }


        UiScrollable scrollableOptions = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
        try {
            UiObject puzzleOptions = ourDevice.findObject(new UiSelector().textContains("Puzzle Timer"));
            UiObject puzzleDifficulty = ourDevice.findObject(new UiSelector().textContains("Set Puzzle Difficulty"));
            UiObject setUiImmersion = ourDevice.findObject(new UiSelector().textContains("Set UI immersion"));
            UiObject autoSave = ourDevice.findObject(new UiSelector().textContains("Auto Save"));
            scrollableOptions.scrollIntoView(puzzleOptions);
            scrollableOptions.scrollIntoView(puzzleDifficulty);
            scrollableOptions.scrollIntoView(setUiImmersion);
            scrollableOptions.scrollIntoView(autoSave);

        } catch (UiObjectNotFoundException e) {
            fail("All settings options were not displayed");
        }
    }


    //Test that clicking the back button in settings page takes us back to main menu
//    @Ignore("Working test")
    @Test
    public void testSettingsBackButtonNavigation() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar").className("android.view.ViewGroup"));
        try {
            Log.d(INSTRUTEST, Integer.toString(actionBar.getChildCount()));
            UiObject backButton = actionBar.getChild(new UiSelector().index(0));
            backButton.click();
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        UiObject mainMenuFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen"));
        if (!mainMenuFragment.exists()) {
            fail("Clicking back button did not display main menu");
        }
    }


    //Test that the difficulty Text correctly displays difficulty selected in Settings page on the
    // Puzzle page
    @Test
    public void testSetDifficultyDisplaysOnPuzzleFragment() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }
        UiObject settingsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        try {
            UiObject timerLayout = settingsFragment.getChild(new UiSelector().index(1));
            UiObject timerLayout1 = timerLayout.getChild(new UiSelector().index(1));
            UiObject switchWidget = timerLayout1.getChild(new UiSelector().resourceId("com.echo.wordsudoku:id/switchWidget"));
            if (switchWidget.isChecked()) {
                switchWidget.click();
            }
        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        String s = "";
        try {
            UiObject difficultySetting = settingsFragment.getChild(new UiSelector().index(3));
            UiObject difficultyLayout = difficultySetting.getChild(new UiSelector().index(0));
            UiObject difficultySelected = difficultyLayout.getChild(new UiSelector().index(1));
            s = difficultySelected.getText().toLowerCase();
            Log.d(INSTRUTEST, s);
        } catch (UiObjectNotFoundException e) {
            fail("Difficulty not found");
        }

        ourDevice.pressBack();

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start classic game
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzle button was not displayed");
        }

        //Start classic game
        UiObject timerDifficultyButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        try {
            String b = timerDifficultyButton.getText().toLowerCase();
            Log.d(INSTRUTEST, b);
            if (!s.equals(b)) {
                fail("Correct difficulty was not displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("Difficulty/Timer button was not displayed");
        }
    }


    //Test that clicking the auto save button in the settings correctly updates the subtext below Auto Save
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsAutoSaveDisplay() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        try {
            UiObject settingsView = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
            UiObject autoSave = settingsView.getChild(new UiSelector().index(7));
            UiObject switchCase = autoSave.getChild(new UiSelector().instance(1));
            UiObject autoSaveSwitch = switchCase.getChild(new UiSelector().instance(0));

            Log.d(INSTRUTEST, Boolean.toString(autoSaveSwitch.isChecked()));

            if (autoSaveSwitch.isChecked()) {
                autoSaveSwitch.click();
                UiObject autoSaveSwitchOff = ourDevice.findObject(new UiSelector().textContains("Auto Save is off").resourceId("android:id/summary"));
                if (!autoSaveSwitchOff.exists()) {
                    fail("Switch did not update display to off");
                }
            } else {
                autoSaveSwitch.click();
                UiObject autoSaveSwitchOn = ourDevice.findObject(new UiSelector().textContains("Auto Save is on").resourceId("android:id/summary"));
                if (!autoSaveSwitchOn.exists()) {
                    fail("Switch did not update display to on");
                }
                autoSaveSwitch.click();
            }


        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();


    }

    //Test that clicking the text to speech switch correctly updates the display below text to speech
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsTextToSpeechDisplay() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        try {
            UiObject settingsView = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
            UiObject ttsOption = settingsView.getChild(new UiSelector().index(9));
            UiObject switchCase = ttsOption.getChild(new UiSelector().instance(1));
            UiObject ttsSwitch = switchCase.getChild(new UiSelector().instance(0));

            Log.d(INSTRUTEST, Boolean.toString(ttsSwitch.isChecked()));

            if (ttsSwitch.isChecked()) {
                ttsSwitch.click();
                UiObject ttsSwitchOff = ourDevice.findObject(new UiSelector().textContains("Listening Comprehension mode is off").resourceId("android:id/summary"));
                if (!ttsSwitchOff.exists()) {
                    fail("Switch did not update display to off");
                }
            } else {
                ttsSwitch.click();
                UiObject ttsSwitchOn = ourDevice.findObject(new UiSelector().textContains("Words will be shown as numbers which will be pronounced when you click them").resourceId("android:id/summary"));
                if (!ttsSwitchOn.exists()) {
                    fail("Switch did not update display to on");
                }
                ttsSwitch.click();
            }


        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();


    }


    //Test that clicking the puzzle difficulty displays a puzzle difficulty dialog
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsDifficultyDialogDisplay() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        try {
            UiObject settingsView = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
            UiObject difficultyButton = settingsView.getChild(new UiSelector().index(3));
            try {
                difficultyButton.click();

            } catch (UiObjectNotFoundException e) {
                fail("Difficulty button was not display");
            }

            UiObject difficultyDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/parentPanel"));
            if (!difficultyDialog.exists()) {
                fail("Select Puzzle Difficulty Dialog did not display");
            }

        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();


    }


    //Test that clicking the puzzle difficulty displays a puzzle difficulty dialog
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsDifficultyButtonDisplay() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        String difficulty = "";

        try {
            UiObject settingsView = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
            UiObject difficultyButton = settingsView.getChild(new UiSelector().index(3));
            try {
                difficultyButton.click();

            } catch (UiObjectNotFoundException e) {
                fail("Difficulty button was not display");
            }

            UiObject difficultyOption1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/select_dialog_listview"));
            try {
                difficulty = difficultyOption1.getChild(new UiSelector().index(5)).getText();
                difficultyOption1.getChild(new UiSelector().index(5)).click();
            } catch (UiObjectNotFoundException e) {
                fail("Difficulty option was not found");
            }



        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        UiObject difficultyOption1 = ourDevice.findObject(new UiSelector().textContains(difficulty));
        if (!difficultyOption1.exists()) {
            fail("Difficulty display was not updated");
        }



        try {
            UiObject settingsView = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
            UiObject difficultyButton = settingsView.getChild(new UiSelector().index(3));
            try {
                difficultyButton.click();

            } catch (UiObjectNotFoundException e) {
                fail("Difficulty button was not display");
            }

            UiObject difficultyOption2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/select_dialog_listview"));
            try {
                difficultyOption2.getChild(new UiSelector().index(0)).click();
            } catch (UiObjectNotFoundException e) {
                fail("Difficulty option was not found");
            }



        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }


        ourDevice.pressBack();
        ourDevice.pressBack();


    }







}


