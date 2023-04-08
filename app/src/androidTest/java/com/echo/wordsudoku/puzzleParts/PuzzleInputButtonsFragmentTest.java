package com.echo.wordsudoku.puzzleParts;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleInputButtonsFragmentTest {


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






    //Test that all buttons are displayed correctly in a puzzle in a horizontal orientation and that clicking
    // them displays the correct button text in an empty cell
//    @Ignore("Working test")
    @Test
    public void testWordInsertCellDisplayMatch() {
        int dim = 4;

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
            fail("Custom Sized button was not displayed");
        }

        //Choose puzzle size dialog
        UiObject choosePuzzleSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/size_radio_group").className("android.widget.RadioGroup"));
        try {
            UiObject sizeSelection = choosePuzzleSizeDialog.getChild(new UiSelector().textContains(Integer.toString(dim)));
            sizeSelection.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size selection was not found");
        }

        UiObject doneChooseSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneChooseSizeDialog.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button in Choose size dialog was not found");
        }

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));

        int iterateThroughButtons = 1;
        for (int i = 0; i < dim * dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject someInputButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id" + iterateThroughButtons));
                    someInputButton.click();

                    ourDevice.pressBack();
                    UiObject cancelButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button3").className("android.widget.Button"));
                    try {
                        cancelButton.click();
                    } catch (UiObjectNotFoundException e) {
                        fail("Cancel button was not displayed");
                    }

                    if(!someCell.getText().equals(someInputButton.getText().toLowerCase())) {
                        fail("Cell display and inserted word did not match: " + someCell.getText() + " " + someInputButton.getText());
                    }

                }
            } catch (UiObjectNotFoundException e) {
                fail("Not all cells or buttons were displayed: " + iterateThroughButtons);
            }
            iterateThroughButtons = (iterateThroughButtons % dim) + 1;
        }


        ourDevice.pressBack();
        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed");
        }
    }



}


