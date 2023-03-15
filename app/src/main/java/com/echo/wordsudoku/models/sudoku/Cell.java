package com.echo.wordsudoku.models.sudoku;


import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.Writable;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * The Cell class represents a cell in the board.
 * It contains the content of the cell (content: WordPair), whether the cell is editable or not (isEditable: boolean), whether the cell is empty or not (isEmpty: boolean),
 * and the language of the cell (language: int from BoardLanguage class).
 *
 * If the cell is empty, the content of the cell is null.
 * If the cell is not editable, the content of the cell cannot be changed.
 * If the cell is editable, the content of the cell can be changed.
 * The language of the cell is the language of the content of the cell.
 * If the cell is empty, the language of the cell is the default language.
 *
 * Usage:
 * Cell cell = new Cell(content, isEditable, language);
 * cell.getContent();
 * cell.setContent(content);
 * cell.isEditable();
 * cell.setEditable(isEditable);
 * cell.isEmpty();
 *
 *
 * Below is an example of a 4x4 sudoku puzzle with 16 cells:
 * -----------------------------------------
 * |  Cell   |         |         |         |
 * -----------------------------------------
 * |         |         |         |         |
 * -----------------------------------------
 * |         |   Cell  |         |         |
 * -----------------------------------------
 * |         |         |         |         |
 * -----------------------------------------
 *
 * Cell = {content: 'The WordPair object this cell contains',
 * isEditable: 'Is this cell editable and user can insert or no',
 * isEmpty: 'Is this cell empty or already filled',
 * language: 'Language of the content of the cell'}
 *
 *
 * @author eakbarib
 *
 * @version 1.0
 */

public class Cell implements Writable {

    // content: the content of the cell
    // by default, the content of the cell is null which means it is empty
    private WordPair content = null;

    // isEmpty: whether the cell is empty or not
    private boolean isEmpty = true;

    // isEditable: whether the cell is editable or not
    private boolean isEditable = true;

    // language: the language of the cell
    private int language;

    /* @constructor
     * @param content: the content of the cell
     * @param isEditable: whether the cell is editable or not
     * @param language: the language of the cell
     * Creates a cell with the given content, whether the cell is editable or not, and the language of the cell
     */

    /* @constructor
     * @param content: the content of the cell
     * @param language: the language of the cell
     * Creates a cell with the given content and the language of the cell
     */

    public Cell(WordPair content, int language) throws NullPointerException {
        if (content == null) {
            throw new NullPointerException("You cannot pass null to the constructor to initialize the content of the cell to null. Use Cell(language) instead.");
        }
        setContent(content);
        setLanguage(language);
        this.isEmpty = false;
    }

    /* @constructor
     * @param content: the word pair of the cell
     * @param language: the language of the cell
     * @param isEditable: whether the cell is editable or not
     */
    public Cell(WordPair content, boolean isEditable, int language) {
        this(content, language);
        setEditable(isEditable);
    }

    /* @constructor
     * @param content: the content of the cell
     * @param isEditable: whether the cell is editable or not
     * @param language: the language of the cell
     * @param isEmpty: whether the cell is empty or not
     * Creates a cell with the given content, whether the cell is editable or not, the language
     *  of the cell, and whether the cell is empty or not
     */
    public Cell(WordPair content, boolean isEditable, int language, boolean isEmpty) {
        this.content = content;
        this.isEmpty = isEmpty;
        this.isEditable = isEditable;
        this.language = language;
    }

    /* @constructor
     * @param cell: the cell to be copied
     * Performs a deep copy of the given cell
     */

    public Cell(Cell cell) {
        if (cell.getContent()!=null) {
            setContent(cell.getContent());
        }
        setEditable(cell.isEditable());
        setLanguage(cell.getLanguage());
        this.isEmpty = cell.isEmpty();
    }

    /* @constructor
     * @param content: the content of the cell
     * Creates a cell with the given content and the default language
     */

    public Cell(WordPair content) {
        this(content, BoardLanguage.defaultLanguage());
    }

    /* @constructor
     * @param language: the language of the cell
     * Creates an empty cell with the default (null) content and the given language
     */
    public Cell(int language) {
        setLanguage(language);
    }

    /* @constructor
     * Creates an empty cell with the default (null) content and the default language (English)
     */

    public Cell() {
        setLanguage(BoardLanguage.defaultLanguage());
    }

    // getters and setters

    public WordPair getContent() {
        return content;
    }


    public void setContent(WordPair content) {
        if (content == null) {
            throw new NullPointerException("You cannot use setContent() to set the content of the cell to null. Use clear() instead.");
        }
        this.content = content;
        // if the content is not null, the cell is not empty
        this.isEmpty = false;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public int getLanguage() {
        return language;
    }


    // setLanguage() throws an IllegalArgumentException if the language is not valid
    public void setLanguage(int language) throws IllegalArgumentException {
        if (language != BoardLanguage.ENGLISH && language != BoardLanguage.FRENCH) {
            throw new IllegalArgumentException("Invalid language name");
        }
        this.language = language;
    }

    /* isEqual() throws a NullPointerException if the content of the cell is null
     * @param cell: the cell to be compared with
     * @return true if the content of the cell is equal to the content of the given cell, false otherwise
     * It calls the isEqual() method of the WordPair class to compare the content of the cells
     * @see WordPair#isEqual(WordPair)
     *  */
    public boolean isEqual(Cell cell) throws NullPointerException {
        try {
            return content.isEqual(cell.getContent());
        } catch (NullPointerException e) {
            throw new RuntimeException("Cell content is null");
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void clear() {
        content = null;
        this.isEmpty = true;
    }


    /* @return the content of the cell in the language of the cell in String format.
     * @see WordPair#getEnglishOrFrench(int)
     */
    @Override
    public String toString() {
        return content.getEnglishOrFrench(getLanguage());
    }

    /* @method to covert the Cell Object and its WordPair into json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (this.getContent() != null) {
            json.put("content", this.getContent().toJson());
        } else {
            json.put("content", JSONObject.NULL);
        }
        json.put("language", this.getLanguage());
        json.put("isEmpty", this.isEmpty());
        json.put("isEditable", this.isEditable());
        return json;
    }

    // equals method for comparison in testing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return isEmpty == cell.isEmpty && isEditable == cell.isEditable && language == cell.language && content.equals(cell.content);
    }


}
