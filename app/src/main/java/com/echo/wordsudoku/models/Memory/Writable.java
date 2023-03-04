package com.echo.wordsudoku.models.Memory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Writable Interface
 * the interface allows any savable object to implement the toJson() function
 * which follows a different procedure in implementation depending on the field of the
 * target class
 *
 */

public interface Writable {


    // @method any object that should be written into the puzzle.json
    // overrides this method
    JSONObject toJson() throws JSONException;
}
