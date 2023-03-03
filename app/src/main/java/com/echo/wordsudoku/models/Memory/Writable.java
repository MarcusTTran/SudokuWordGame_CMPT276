package com.echo.wordsudoku.models.Memory;

import org.json.JSONException;
import org.json.JSONObject;

public interface Writable {


    // @method any object that should be written into the puzzle.json
    // overrides this method
    JSONObject toJson() throws JSONException;
}
