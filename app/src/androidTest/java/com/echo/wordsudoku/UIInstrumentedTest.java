package com.echo.wordsudoku;

import static android.os.SystemClock.sleep;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.By;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UIInstrumentedTest {


    //DUE TO SLOW SPEED OF UI TESTS I HAVE DISABLED SOME OF MY TESTS FOR NOW SO THAT I CAN WRITE AND TEST EACH INDIVIDUAL TEST MORE EFFICIENTLY
    //THE TESTS THAT ARE WORKING HAVE BEEN HIGHLIGHTED WITH THE GREEN TO-DO AS "WORKING TEST"

    private UiDevice ourDevice;
    final String INSTRUTEST = "INSTRU_TESTING";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String BASIC_SAMPLE_PACKAGE = "com.echo.wordsudoku";

    //@Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.echo.wordsudoku", appContext.getPackageName());
    }

    //Test brings us to home page then starts up
    @Before
    public void testSetUp() {
        ourDevice = UiDevice.getInstance(getInstrumentation());
        ourDevice.pressHome();

        final String launcherPackage = ourDevice.getLauncherPackageName();
        if (launcherPackage.equals(null)) {
            fail();
        }

        ourDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent1 = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
        ourDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    //Rotate our screen using this method
    //@Test
    public void rotateScreen() {
        try {
            ourDevice.setOrientationLeft();
        } catch (android.os.RemoteException e) {
            fail();
        }
    }

    //Test that the timer correctly appears on the puzzles fragment when turned on
    @Ignore("Working test")
    @Test
    public void testTimerDisplayOnPuzzleFragment() {
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
            if (!switchWidget.isChecked()) {
                switchWidget.click();
            }
        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }
        ourDevice.pressBack();

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

        //Start classic puzzle
        UiObject timerDisplay = ourDevice.findObject(new UiSelector().className("android.widget.TextView").textContains("00:"));
        if (!timerDisplay.exists()) {
            fail("Timer is not correctly being displayed when turned on");
        }

    }


    //Test that the timer switch in the settings correctly updates subtext below Timer Puzzle header
    @Ignore("Working test")
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

    //Test that the save dialog correctly appears when pressing back
    @Ignore("Working test")
    @Test
    public void testSaveDialogDisplaysOnBackPress() {
        //For CANCEL, YES, NO
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

        //Check that the save dialog content exists
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog box did not appear on back press");
        }

        for (int i = 0; i < totalButtons; i++) {
            UiObject button = ourDevice.findObject(new UiSelector().resourceId("android:id/button"+ (i + 1)));
            if (!button.exists()) {
                fail("All buttons in save dialog were not displayed");
            }
        }

    }

    //Check that the puzzle page displays all necessary buttons and TextViews to the user
    @Ignore("Working test")
    @Test
    public void testPuzzlePageDisplay() {
        int dim = 9;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        sleep(500);

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        if (!doneButton.exists()) {
            fail("Done Button was not displayed");
        }

        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        if (!resetButton.exists()) {
            fail("Reset Button was not displayed");
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        if (!dictionaryButton.exists()) {
            fail("Dictionary Button was not displayed");
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        if (!helpButton.exists()) {
            fail("Help Button was not displayed");
        }

        for(int i = 0; i < dim; i++) {
            UiObject inputButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id" + (i + 1)).className("android.widget.Button"));
            if (!inputButton.exists()) {
                fail("The " + i + " button was not displayed");
            }
        }

        UiObject timerDisplay = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        if (!timerDisplay.exists()) {
            fail("Timer/Difficulty was not displayed");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().className("android.widget.FrameLayout"));
        try {
            UiObject backButton = actionBar.getChild(new UiSelector().index(1));
            UiObject kebabButton = actionBar.getChild(new UiSelector().index(3));
            if (!backButton.exists()) {
                fail("Back button was not shown");
            }
            if (!kebabButton.exists()) {
                fail("Kebab button was not shown");
            }
        } catch (UiObjectNotFoundException e) {
            fail();
        }

    }

    //Test that the puzzle page displays all necessary buttons and TextViews to the user
    @Ignore("Working test")
    @Test
    public void testPuzzlePageDisplayHorizontal() {
        int dim = 9;

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
        sleep(500);

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        if (!doneButton.exists()) {
            fail("Done Button was not displayed");
        }

        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        if (!resetButton.exists()) {
            fail("Reset Button was not displayed");
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        if (!dictionaryButton.exists()) {
            fail("Dictionary Button was not displayed");
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        if (!helpButton.exists()) {
            fail("Help Button was not displayed");
        }

        for(int i = 0; i < dim; i++) {
            UiObject inputButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id" + (i + 1)).className("android.widget.Button"));
            if (!inputButton.exists()) {
                fail("The " + i + " button was not displayed");
            }
        }

        UiObject timerDisplay = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        if (!timerDisplay.exists()) {
            fail("Timer/Difficulty was not displayed");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().className("android.widget.FrameLayout"));
        try {
            UiObject backButton = actionBar.getChild(new UiSelector().index(1));
            UiObject kebabButton = actionBar.getChild(new UiSelector().index(3));
            if (!backButton.exists()) {
                fail("Back button was not shown");
            }
            if (!kebabButton.exists()) {
                fail("Kebab button was not shown");
            }
        } catch (UiObjectNotFoundException e) {
            fail();
        }

    }

    //Test that clicking the Custom Words button on the Choose Puzzle Mode with no custom words
    //entered, takes the user to the Choose Custom Words page
    @Test
    public void testCustomWordsNoCustomWords() {
        fail();
    }

    //TODO: Use UiScrollable to achieve this test (only needs to be for sizes 9 and 12)
    //Test that the correct amount of edit texts are displayed for the option selected in the dropdown
    // when in the choose custom words fragment
    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayed12x12Horizontal() {
        int totalEditText = 12;

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

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(3)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown options was not displayed");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        UiObject entryBox2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));

        int EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox1.getChildCount(); i++) {
                UiObject edittext = entryBox1.getChild(new UiSelector().index(i));

                UiScrollable entryBoxScrollView = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
                try {
                    entryBoxScrollView.scrollIntoView(edittext);
                } catch (UiObjectNotFoundException e) {
                    fail("ScrollView not found");
                }

                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed; Only " + EditTextCounter);
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        //Scroll the ScrollView back up to count the 2nd row
        UiScrollable scrollBackUp = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
        try {
            scrollBackUp.flingBackward();
        } catch (UiObjectNotFoundException e) {
            fail("ScrollView not found");
        }

        EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox2.getChildCount(); i++) {
                UiObject edittext = entryBox2.getChild(new UiSelector().index(i));

                UiScrollable entryBoxScrollView = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
                try {
                    entryBoxScrollView.scrollIntoView(edittext);
                } catch (UiObjectNotFoundException e) {
                    fail("ScrollView not found");
                }

                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed; Only " + EditTextCounter);
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }
    }

    //Test that the correct amount of edit texts are displayed for the option selected in the dropdown
    // when in the choose custom words fragment
    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayed12x12() {
        int totalEditText = 12;

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(3)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown options was not displayed");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        UiObject entryBox2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));

        int EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox1.getChildCount() - 1; i++) {
                UiObject edittext = entryBox1.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox2.getChildCount() - 1; i++) {
                UiObject edittext = entryBox2.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }
    }




    //Test that the correct amount of edit texts are displayed for the option selected in the dropdown
    // when in the choose custom words fragment
    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayed4x4() {
        int totalEditText = 4;

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(0)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown option was not displayed");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        UiObject entryBox2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));

        int EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox1.getChildCount() - 1; i++) {
                UiObject edittext = entryBox1.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox2.getChildCount() - 1; i++) {
                UiObject edittext = entryBox2.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditText) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }
    }


    //Test that when users click the puzzle language button, the button text displays the
    //language it has been set to
    @Ignore("Working test")
    @Test
    public void testPuzzleLanguageButtonUpdate() {
        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        try {
            puzzleLanguageButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle language button not found");
        }

        UiObject puzzleLanguageUpdateText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        try {
            if (puzzleLanguageUpdateText.getText().equals("Puzzle Language : English")) {
                fail("Puzzle Language Button did not display set language correctly");
            }
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle Language Button text did not update on click");
        }

    }

    //Test that when clicking Custom Words button the user is correctly taken to Choose custom words page
    @Ignore("Working test")
    @Test
    public void testCustomWordsButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Click custom words button
        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        try {
            customWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Words button was not displayed");
        }

        //Check if chooseCustomWordsFragment is being displayed
        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment").className("android.view.ViewGroup"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose custom words fragment was not displayed");
        }
    }

    //Test that all buttons are displayed in the Choose Puzzle Mode page in a horizontal landscape
    @Ignore("Working test")
    @Test
    public void testChoosePuzzleModeAllButtonsDisplayedHorizontal() {
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
            fail("New Game button was not displayed");
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        if (!classicPuzzleButton.exists()) {
            fail("Classic Puzzle button was not displayed");
        }

        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        if (!customSizedButton.exists()) {
            fail("Custom Size button was not displayed");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        if (!customWordsButton.exists()) {
            fail("Custom Words button was not displayed");
        }

    }

    //Test that all buttons are displayed in the Choose Puzzle Mode page
    @Ignore("Working test")
    @Test
    public void testChoosePuzzleModeAllButtonsDisplayed() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        if (!classicPuzzleButton.exists()) {
            fail("Classic Puzzle button was not displayed");
        }

        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        if (!customSizedButton.exists()) {
            fail("Custom Size button was not displayed");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        if (!customWordsButton.exists()) {
            fail("Custom Words button was not displayed");
        }

    }


    //Test that all main menu buttons are correctly displayed in a horizontal layout
    @Ignore("Working test")
    @Test
    public void testAllButtonsDisplayedMainMenuHorizontal() {
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



    //Test that all buttons are displayed in a 9x9 puzzle in a horizontal layout and clicking them correctly
    //displays the buttons text in an empty cell
    @Ignore("Working test")
    @Test
    public void testAllButtonsDisplayedAndClickable9x9PuzzleHorizontal() {
        int DIM = 9;

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
        sleep(500);

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));
        UiObject newCell = null;
        int saveCell = -1;

        try {
            newCell = sudokuBoard.getChild(new UiSelector().descriptionContains("contains EMPTYCELL"));
            newCell.click();
        } catch (UiObjectNotFoundException e) {
            fail("Empty cell not found");
        }


        for (int i = 0; i < DIM; i++) {
            UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id" + (i + 1)));
            try {
                Log.d(INSTRUTEST, "pass: " + (i + 1));
                button1.click();
            } catch (UiObjectNotFoundException e) {
                fail();
            }

            try {
                newCell = sudokuBoard.getChild(new UiSelector().descriptionContains(button1.getText().toLowerCase()));
            } catch (UiObjectNotFoundException e) {
                fail();
            }

            try {
                Log.d(INSTRUTEST, newCell.getText() + "  " + button1.getText().toLowerCase());
                if (!newCell.getText().equals(button1.getText().toLowerCase())) {
                    fail();
                }
            } catch (UiObjectNotFoundException e) {
                fail();
            }
        }
    }









    //Test that all buttons are displayed in a 9x9 puzzle in a horizontal layout and clicking them correctly
    //displays the buttons text in an empty cell
    @Ignore("Working test")
    @Test
    public void testAllButtonsDisplayedAndClickable9x9Puzzle() {
        int DIM = 9;

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

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));
        UiObject newCell = null;
        int saveCell = -1;

        try {
            newCell = sudokuBoard.getChild(new UiSelector().descriptionContains("contains EMPTYCELL"));
            newCell.click();
        } catch (UiObjectNotFoundException e) {
            fail("Empty cell not found");
        }


        for (int i = 0; i < DIM; i++) {
            UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id" + (i + 1)));
            try {
                Log.d(INSTRUTEST, "pass: " + (i + 1));
                button1.click();
            } catch (UiObjectNotFoundException e) {
                fail();
            }

            try {
                newCell = sudokuBoard.getChild(new UiSelector().descriptionContains(button1.getText().toLowerCase()));
            } catch (UiObjectNotFoundException e) {
                fail();
            }

            try {
                Log.d(INSTRUTEST, newCell.getText() + "  " + button1.getText().toLowerCase());
                if (!newCell.getText().equals(button1.getText().toLowerCase())) {
                    fail();
                }
            } catch (UiObjectNotFoundException e) {
                fail();
            }
        }
    }



    //Test that tapping the choose custom words button takes us to the choose custom words fragment
    @Ignore("Working test")
    @Test
    public void testChooseCustomWordsButtonNavigation() {
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

    //TODO: WORKING TEST
    //Test that clicking the new game button takes you to the Choose puzzle mode fragment
    //@Test
    public void testNewGameButton() {
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


    //Test that clicking the back button in settings page takes us back to main menu
    @Ignore("Working test")
    @Test
    public void testSettingsBackButton() {
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


    //Test clicking the Settings button correctly displays the Settings fragment along with options
    @Ignore("Working test")
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


    //Test that all buttons are correctly displayed on the main menu
    @Ignore("Working test")
    @Test
    public void testAllButtonsDisplayedMainMenu() {
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



    //Test clicking the Exit button correctly exits the app
    @Ignore("Working test")
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


    //Test that all 4 radiobutton sizes are displayed in the custom word fragments puzzle size dropdown
    @Ignore("Working test")
    @Test
    public void testAllCustomSizedPuzzlesDisplayed() {

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dropdownPuzzleSizes = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/customPuzzleSizeSelect"));
        if (!dropdownPuzzleSizes.exists()) {
            fail();
        }

        UiObject puzzle4x4Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_12x12_puzzle"));
        UiObject puzzle12x12Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_6x6_button"));
        UiObject puzzle6x6Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_9x9_puzzle"));
        UiObject puzzle9x9Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button"));
        if (!puzzle4x4Option.exists()) {
            fail("4x4 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle6x6Option.exists()) {
            fail("6x6 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle9x9Option.exists()) {
            fail("9x9 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle12x12Option.exists()) {
            fail("12x12 option was not displayed in Choose Custom Size pop up");
        }
    }




    //Test that the rules dialog correctly pops up and displays instructions
    @Ignore("Working test")
    @Test
    public void testRulesPopUpDialog() {
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

        UiObject rulesButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules Button not found");
        }

        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        if (!rulesInfoText.exists()) {
            fail("Rules dialog properly open");
        }

        UiObject rulesInfo = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesInformation"));
        if (!rulesInfo.exists()) {
            fail("Rules are not being displayed");
        }


    }



    //Test that the rules dialog exit button correctly closes rules dialog
    @Ignore("Working test")
    @Test
    public void testRulesDialogExitButton() {
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

        UiObject rulesButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules Button not found");
        }

        UiObject rulesExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/rulesExitButton"));
        try {
            rulesExitButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button does not exist");
        }

        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        if (rulesInfoText.exists()) {
            fail("Rules dialog did not properly close");
        }

    }




    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
    @Ignore("Working test")
    @Test
    public void testDictionaryPeeksLimitDialog() {
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

        for (int i = 0 ; i < 2; i++) {
            try {
                dictionaryButton.click();
            } catch (UiObjectNotFoundException e) {
                fail("Dictionary button not found");
            }
            UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton").className("android.widget.ImageButton"));
            try {
                dictionaryExitButton.click();
            } catch (UiObjectNotFoundException e) {
                fail("Dictionary exit button not found");
            }
        }

        UiObject dictionaryFinalButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        try {
            dictionaryFinalButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Dictionary button not found");
        }

        UiObject dictionaryLimitDialogPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryLimit").className("android.widget.TextView"));
        if (!dictionaryLimitDialogPopUp.exists()) {
            fail("Dictionary limit failed to appear even after 2 peeks");
        }
    }


    //Test that clicking on the 3 dots opens up a dialog
    @Ignore("Working test")
    @Test
    public void testKebabButtonInPuzzleFragmentDisplaysOptions() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject listView = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        if (!listView.exists()) {
            fail("Kebab did load options page listview");
        }


        UiObject doneButton = ourDevice.findObject(new UiSelector().text("Done").className("android.widget.TextView"));
        UiObject rulesButton = ourDevice.findObject(new UiSelector().text("Rules").className("android.widget.TextView"));
        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().text("Dictionary").className("android.widget.TextView"));
        UiObject savePuzzleButton = ourDevice.findObject(new UiSelector().text("Save Puzzle").className("android.widget.TextView"));
        UiObject mainMenuButton = ourDevice.findObject(new UiSelector().text("Main Menu").className("android.widget.TextView"));
        UiObject exitButton = ourDevice.findObject(new UiSelector().text("Exit").className("android.widget.TextView"));

        if (!doneButton.exists()) {
            fail("Done button not displayed");
        }
        if (!rulesButton.exists()) {
            fail("Rules button not displayed");
        }
        if (!dictionaryButton.exists()) {
            fail("Dictionary button not displayed");
        }
        if (!savePuzzleButton.exists()) {
            fail("Save Puzzle button not displayed");
        }
        if (!mainMenuButton.exists()) {
            fail("Main Menu not displayed");
        }
        if (!exitButton.exists()) {
            fail("Exit button not displayed");
        }

    }


    //Test that we can open rules from Kebab menu
    @Ignore("Working test")
    @Test
    public void testKebabRulesButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject rulesButton = ourDevice.findObject(new UiSelector().textContains("Rules").className("android.widget.TextView"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button not found");
        }

        UiObject popUpRules = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesInformation"));
        UiObject rulesHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesHeader"));
        UiObject rulesExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/rulesExitButton"));

        if (!rulesInfoText.exists() || !rulesHeader.exists() || !rulesExitButton.exists() || !popUpRules.exists()) {
            fail("Rules pop up was not properly displayed");
        }


    }

    //Test that we can open the dictionary pop up from Kebab menu
    @Ignore("Working test")
    @Test
    public void testKebabDictionaryButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().textContains("Dictionary").className("android.widget.TextView"));
        try {
            dictionaryButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button not found");
        }

        UiObject popupDictionary = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_DictionaryBox"));
        UiObject dictionaryWordList1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang2"));
        UiObject dictionaryWordList2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang1"));
        UiObject dictionaryHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryHeader"));
        UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton"));

        if (!dictionaryWordList1.exists() || !dictionaryWordList2.exists() ||!dictionaryHeader.exists() || !dictionaryExitButton.exists() || !popupDictionary.exists()) {
            fail("Dictionary pop up was not properly displayed");
        }
    }

    //Test that the entry box limits the word length to 12
    @Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxMaxLength() {
        String longUserInputtedString = "awehuifuiawdghwuawidhauiwdhuawidawuidhawuihduwhduawidjawdjiwjawda";

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));

        try {
            UiObject editText = entryBox1.getChild(new UiSelector().index(1));
            editText.click();
            editText.setText(longUserInputtedString);
            String s = editText.getText();
            if (s.length() > 12) {
                fail("Words exceed the length of 12");
            }

        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }



    }

    //TODO:
    //Test that the entry box limits the word length to 12
    //@Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxBlank() {
        String longUserInputtedString = "awehuifuiawdghwuawidhauiwdhuawidawuidhawuihduwhduawidjawdjiwjawda";

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));

        try {
            UiObject editText = entryBox1.getChild(new UiSelector().index(1));
            editText.click();
            editText.setText(longUserInputtedString);
            String s = editText.getText();
            if (s.length() > 12) {
                fail("Words exceed the length of 12");
            }

        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        fail();

    }

    //TODO:
    //Test that entering numbers into custom words shows error dialog
    //@Ignore("Working test")
    @Test
    public void testCustomWordsNumbersError() {
        fail();
    }

    @Test
    public void testSettingsHorizontal() {
        fail();
    }

    @Test
    public void testGameLoseDisplay() {
        fail();
    }

    @Test
    public void testGameWinDisplay() {
        fail();
    }

    @Test
    public void testSaveDialogCancel() {
        fail();
    }

    @Test
    public void testResetButton() {
        fail();
    }

    //Test selecting exit in the kebab menu closes the app
    @Ignore("Working test")
    @Test
    public void testKebabExitButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().textContains("Exit").className("android.widget.TextView"));
        try {
            exitButton.click();
            sleep(1000);
        } catch (UiObjectNotFoundException e) {
            fail("Exit button not found");
        }

        UiObject puzzlePage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment").className("android.view.ViewGroup"));
        UiObject mainMenuScreen = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen").className("android.view.ViewGroup"));

        if (puzzlePage.exists() || mainMenuScreen.exists()) {
            fail("Exit button did not work");
        }
    }


    // Test that a user cannot override a permanent cell
    @Ignore("Working test")
    @Test
    public void testCellNotOverridable() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        String permanentCellValue = "WRONG_VALUE";

        UiObject permanentCell = ourDevice.findObject(new UiSelector().descriptionContains("contains text").className("android.view.View"));
        try {
            permanentCellValue = permanentCell.getText();
            permanentCell.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail();
        }

        UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id1"));
        try {
            button1.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail();
        }

        try {
            assertTrue(permanentCell.getText().equals(permanentCellValue));
        } catch (UiObjectNotFoundException e) {
            fail();
        }
    }


    // Test that a non-permanent cell when overridden displays correct text
    @Ignore("Working test")
    @Test
    public void testOverridableCell() {

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

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));
        UiObject newCell = null;
        int saveCell = -1;
        for (int i = 0; i < 81; i++) {
            try {
                newCell = sudokuBoard.getChild(new UiSelector().descriptionContains("contains EMPTYCELL").instance(i));
                newCell.click();
                saveCell = i;
                break;
            } catch (UiObjectNotFoundException e) {
                fail();
            }
        }

        UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id1"));
        try {
            button1.click();
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        try {
            newCell = sudokuBoard.getChild(new UiSelector().instance(saveCell));
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        try {

            Log.d(INSTRUTEST, newCell.getContentDescription());
            String lowerCase = button1.getText().toLowerCase();

            Log.d(INSTRUTEST, newCell.getText() + "  " + lowerCase);

            assertTrue(newCell.getContentDescription().contains(lowerCase));
            assertTrue(newCell.getText().equals(button1.getText().toLowerCase()));
        } catch (UiObjectNotFoundException e) {
            fail();
        }

    }



}