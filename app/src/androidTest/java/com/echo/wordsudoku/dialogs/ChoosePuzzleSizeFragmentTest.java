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








    //Check that the puzzle page displays all necessary buttons and TextViews to the user
//    @Ignore("Working test")
    @Test
    public void testPuzzlePageDisplayAllPuzzleSizes() {
        for (int i = 0; i < puzzleSizes.length; i++) {
            testPuzzlePageDisplayHelper(puzzleSizes[i]);
        }
    }

    public void testPuzzlePageDisplayHelper(int dim) {

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start custom sized puzzle
        UiObject customSizePuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizePuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Sized button was not displayed on Puzzle size " + dim);
        }

        //Choose puzzle size dialog
        UiObject choosePuzzleSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/size_radio_group").className("android.widget.RadioGroup"));
        try {
            UiObject sizeSelection = choosePuzzleSizeDialog.getChild(new UiSelector().textContains(Integer.toString(dim)));
            sizeSelection.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size selection was not displayed on Puzzle size " + dim);
        }

        UiObject doneChooseSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneChooseSizeDialog.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button in Choose size dialog was not displayed on Puzzle size " + dim);
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        if (!doneButton.exists()) {
            fail("Done Button was not displayed on Puzzle size " + dim);
        }

        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        if (!resetButton.exists()) {
            fail("Reset Button was not displayed on Puzzle size " + dim);
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        if (!dictionaryButton.exists()) {
            fail("Dictionary Button was not displayed on Puzzle size " + dim);
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        if (!helpButton.exists()) {
            fail("Help Button was not displayed on Puzzle size " + dim);
        }

        UiObject inputButtons = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/board_input_buttons").className("android.widget.LinearLayout"));
        try {
            for(int i = 0; i < inputButtons.getChildCount(); i++) {
                UiObject buttonLayouts = inputButtons.getChild(new UiSelector().instance(i));
                if (!buttonLayouts.exists()) {
                    fail("All buttons are not displayed on Puzzle size " + dim);
                }
            }
        } catch (UiObjectNotFoundException e) {
            fail("Input buttons are not displayed on Puzzle size " + dim);
        }



        UiObject timerDisplay = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        if (!timerDisplay.exists()) {
            fail("Timer/Difficulty was not displayed on Puzzle size " + dim);
        }

        //Test that all cells are displayed
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed on Puzzle size " + dim);
        }

        for (int i = 0; i < dim * dim; i++) {
            try {
                if (!sudokuBoard.getChild(new UiSelector().instance(i)).exists()) {
                    fail("All cells not displayed on Puzzle size " + dim);
                }
            } catch (UiObjectNotFoundException e) {
                fail("All cells not displayed on Puzzle size " + dim);
            }
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().className("android.widget.FrameLayout"));
        try {
            UiObject backButton = actionBar.getChild(new UiSelector().index(1));
            UiObject kebabButton = actionBar.getChild(new UiSelector().index(3));
            if (!backButton.exists()) {
                fail("Back button was not displayed on Puzzle size " + dim);
            }
            if (!kebabButton.exists()) {
                fail("Kebab button was not displayed on Puzzle size " + dim);
            }
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
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

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        //Start custom sized puzzle
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


