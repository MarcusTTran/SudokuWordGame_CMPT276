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







    //TODO: WORKING TEST
    //Find an empty cell; test that every button is clickable and that clicking each button displays the correct text in selected cell
    //@Test
    public void testAllButtonsWorking9x9() {
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



    //TODO: WORKING TEST
    //Test that tapping the choose custom words button takes us to the choose custom words fragment
    //@Test
    public void testChooseCustomWordsButton() {
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


    //TODO: WORKING TEST
    //Test that clicking the back button in settings page takes us back to main menu
    //@Test
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

        //TODO: WORKING TEST
    //Test clicking the Settings button correctly displays the Settings fragment along with options
    //@Test
    public void testMainMenuSettingsButton() {
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
    //@Test
    public void testAllOptionsDisplayedMainMenu() {
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


    //TODO: WORKING TEST
    //Test clicking the Exit button correctly exits the app
    //@Test
    public void testExitButton() {
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

    //TODO: WORKING TEST
    //Test that all 4 radiobutton sizes are displayed in the custom word fragments puzzle size dropdown
    //@Test
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




    //TODO: WORKING TEST
    //Test that the rules dialog correctly pops up and displays instructions
    //@Test
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



    //TODO: WORKING TEST
    //Test that the rules dialog exit button correctly closes rules dialog
    //@Test
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





    //TODO: WORKING TEST
    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
    //@Test
    public void testDictionaryPeeksLimit() {
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


    //TODO: WORKING TEST
    //Test that clicking on the 3 dots opens up a dialog
    //@Test
    public void testKebabButton() {
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

    //TODO: WORKING TEST
    //Test that we can open rules from Kebab menu
    //@Test
    public void testKebabButtonRules() {
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

        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesInformation"));
        UiObject rulesHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesHeader"));
        UiObject rulesExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/rulesExitButton"));

        if (!rulesInfoText.exists() || !rulesHeader.exists() || !rulesExitButton.exists()) {
            fail("Rules pop up was not properly displayed");
        }


    }



    // TODO: WORKING
    // Test that a user cannot override a permanent cell
    //@Test
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

    // TODO: WORKING
    // Test that a non-permanent cell when overridden displays correct text
    //@Test
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