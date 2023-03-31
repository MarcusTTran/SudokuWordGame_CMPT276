package com.echo.wordsudoku.models.Util;
import static org.junit.Assert.assertEquals;
import com.echo.wordsudoku.models.utility.StringUtility;
import org.junit.jupiter.api.Test;


/**
 * Unit test for StringUtility
 * @author Kousha Amouzesh
 * @version 1.0
 */

public class StringUtilityTest {

    @Test
    public void testSecondsToTimerLabel() {

        assertEquals("00:00", StringUtility.secondsToTimerLabel(0));
        assertEquals("00:01", StringUtility.secondsToTimerLabel(1));
        assertEquals("01:00", StringUtility.secondsToTimerLabel(60));
        assertEquals("01:01", StringUtility.secondsToTimerLabel(61));
        assertEquals("05:59", StringUtility.secondsToTimerLabel(359));
        assertEquals("59:59", StringUtility.secondsToTimerLabel(3599));
    }
}
