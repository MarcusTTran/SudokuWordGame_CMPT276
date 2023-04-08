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
public class ChooseCustomWordsFragmentTest {

    //Warning: These tests class take up to 7 minutes to complete

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


    //Test that entering numbers into custom words displays error dialog
//    @Ignore("Working test")
    @Test
    public void testCustomWordsNumbersErrorDialog() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject clearButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonClearCustomWords"));
        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
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
            fail("Dropdown options was not displayed");
        }


        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));

        try {
            UiObject editText = entryBox1.getChild(new UiSelector().index(1));
            editText.click();
            editText.setText("123");
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
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

        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose Custom Words fragment was not shown after warning pop up was dismissed");
        }
    }


    //Test that a warning dialog is displayed when a user does not enter any text in the Choose Custom Words
    // page then presses confirm
//    @Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxBlank() {

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject clearButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonClearCustomWords"));
        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
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
            fail("Dropdown options was not displayed");
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

        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose Custom Words fragment was not shown after warning pop up was dismissed");
        }



    }


    //Test that the entry box in the choose custom words page limits the word length to 12
//    @Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxMaxLength() {
        String longUserInputtedString = "awehuifuiawdghwuawidhauiwdhuawidawuidhawuihduwhduawidjawdjiwjawda";

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject clearButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonClearCustomWords"));
        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
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
            fail("Dropdown options was not displayed");
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


        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
        }

    }


    //Test that all 4 radiobutton sizes [4, 6, 9, 12]
    // are displayed in the custom word fragments puzzle size options dropdown
//    @Ignore("Working test")
    @Test
    public void testSelectCustomSizedPuzzlesDropdownDisplayed() {

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



    //Test that the correct amount of Entry boxes are displayed for the option selected in the dropdown
    // when in the Choose custom words fragment in landscape orientation
//    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayed() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        for (int i = 0; i < puzzleSizes.length; i++) {
            testCustomWordsPageAllEditTextsDisplayedHelper(puzzleSizes[i], i);
        }
    }

    public void testCustomWordsPageAllEditTextsDisplayedHelper(int totalEditTexts, int index) {

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(index)).click();
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
            if (EditTextCounter != totalEditTexts) {
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
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }
    }


    //Test that the correct amount of entry boxes are displayed for the option selected in the dropdown
    // when in the Choose Custom Words fragment in a Horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayedHorizontalScrollable() {
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

        for (int i = 0; i < puzzleSizes.length; i++) {
            testCustomWordsPageAllEditTextsDisplayedHelperHorizontal(puzzleSizes[i], i);
        }

    }


    public void testCustomWordsPageAllEditTextsDisplayedHelperHorizontal(int totalEditTexts, int index) {

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed on Puzzle size " + totalEditTexts);
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(index)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown options was not displayed on Puzzle size " + totalEditTexts);
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
                    fail("EditText at index " + i + " was not displayed at puzzle size: " + totalEditTexts);
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
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
                    fail("EditText at index " + i + " was not displayed at puzzle size: " + totalEditTexts);
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed; Only " + EditTextCounter);
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        //Scroll the ScrollView back up to count the 2nd row
        scrollBackUp = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
        try {
            scrollBackUp.flingBackward();
        } catch (UiObjectNotFoundException e) {
            fail("ScrollView not found");
        }

    }

    @Test
    public void testCustomWordsPageBackButton() {

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject backButton = ourDevice.findObject(new UiSelector().className("android.widget.ImageButton"));
        try {
            backButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words back button not found");
        }

        UiObject mainMenu = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen"));
        if (!mainMenu.exists()) {
            fail("Back button did not return user to the main menu");
        }

    }

    //Test that Choose custom words clear button is working
//    @Ignore("Working test")
    @Test
    public void testCustomWordsClearButton() {
        String[] sampleFrenchWords = new String[] {"Mal", "Bon", "Il", "Elle"};
        String[] sampleEnglishWords = new String[] {"Bad", "Good", "He", "She"};


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

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("android:id/text1"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject customWordsEntries1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        try {
            for (int i = 0; i < customWordsEntries1.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries1.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                someEntryBox.setText(sampleFrenchWords[i]);
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }

        UiObject customWordsEntries2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));
        try {
            for (int i = 0; i < customWordsEntries2.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries2.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                someEntryBox.setText(sampleEnglishWords[i]);
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }

        UiObject clearButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonClearCustomWords"));
        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
        }


        try {
            for (int i = 0; i < customWordsEntries1.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries1.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                if (!someEntryBox.getText().equals("")) {
                    fail("Entry boxes weren't cleared");
                }
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }

        try {
            for (int i = 0; i < customWordsEntries2.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries2.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                if (!someEntryBox.getText().equals("")) {
                    fail("Entry boxes weren't cleared");
                }
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }


    }


    //Test that entering valid custom words and clicking confirm takes user to puzzle fragment page
//    @Ignore("Working test")
    @Test
    public void testCustomWordsConfirmButton() {
        String[] sampleFrenchWords = new String[] {"Mal", "Bon", "Il", "Elle"};
        String[] sampleEnglishWords = new String[] {"Bad", "Good", "He", "She"};


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

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("android:id/text1"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject customWordsEntries1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        try {
            for (int i = 0; i < customWordsEntries1.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries1.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                someEntryBox.setText(sampleFrenchWords[i]);
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }

        UiObject customWordsEntries2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));
        try {
            for (int i = 0; i < customWordsEntries2.getChildCount() - 1; i++) {
                UiObject someEntryBox = customWordsEntries2.getChild(new UiSelector().instance(i + 1));
                someEntryBox.click();
                someEntryBox.setText(sampleEnglishWords[i]);
            }
        } catch (UiObjectNotFoundException e) {
            fail("Entry boxes not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));
        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button not found");
        }

        UiObject puzzleFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment"));
        if (!puzzleFragment.exists()) {
            fail("Puzzle fragment did not appear after entering valid custom words");
        }

        //Clear the custom words for other tests to work
        ourDevice.pressBack();
        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button not found");
        }

        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject clearButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonClearCustomWords"));
        try {
            clearButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Clear button not found");
        }



    }








}



