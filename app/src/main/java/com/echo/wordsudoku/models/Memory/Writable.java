package com.echo.wordsudoku.models.Memory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Writable Interface
 * the interface allows any savable object to implement the toJson() function
 * which follows a different procedure in implementation depending on the field of the
 * target class that is being saved
 * @author kousha amouzesh
 * @version 1.0
 *
 */

public interface Writable {


    /**
     * toJson() function
     * @return the json object of the target class
     * @throws JSONException
     */
    JSONObject toJson() throws JSONException;
}
