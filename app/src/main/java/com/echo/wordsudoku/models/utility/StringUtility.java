package com.echo.wordsudoku.models.utility;

import java.util.Locale;

public class StringUtility {

    // Convert timer to a string in the format of mm:ss
    public static String  secondsToTimerLabel(int timer) {
        int minutes = timer / 60;
        int seconds = timer % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
