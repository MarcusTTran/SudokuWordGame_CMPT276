package com.echo.wordsudoku.models;


/**
 * The Cell class represents a cell in the board.
 * It contains the content of the cell, whether the cell is editable or not, whether the cell is correct or not,
 * and the language of the cell.
 *
 * @author Echo
 *
 * @version 1.0
 */

public class Cell {

    // content: the content of the cell
    private WordPair content;

    private boolean isEmpty;

    private boolean isEditable;

    // isCorrect: whether the cell is correct or not
    private boolean isCorrect;

    // language: the language of the cell
    private int language;

    /* @constructor
     * @param content: the content of the cell
     * @param isEditable: whether the cell is editable
     * @param isCorrect: whether the cell is correct
     * @param language: the language of the cell
     * @throws IllegalArgumentException if the given language name is invalid
     */

    public Cell(WordPair content, boolean isEditable, boolean isCorrect, int language) {
        setContent(content);
        setEditable(isEditable);
        setCorrect(isCorrect);
        setLanguage(language);
        this.isEmpty = false;
    }

    public Cell(Cell cell) {
        this(cell.getContent(), cell.isEditable(), cell.isCorrect(), cell.getLanguage());
    }

    public Cell(WordPair content, int language) {
        this(content, true, true, language);
    }

    public Cell(WordPair content) {
        this(content, BoardLanguage.defaultLanguage());
    }

    public Cell(int language) {
        this(null, language);
        this.isEmpty = true;
    }

    public Cell() {
        this(BoardLanguage.defaultLanguage());
    }

    public WordPair getContent() {
        return content;
    }

    public void setContent(WordPair content) {
        this.content = content;
        this.isEmpty = false;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) throws IllegalArgumentException {
        if (language != BoardLanguage.ENGLISH && language != BoardLanguage.FRENCH) {
            throw new IllegalArgumentException("Invalid language name");
        }
        this.language = language;
    }

    public boolean isEqual(Cell cell) {
        return content.isEqual(cell.getContent());
    }

    public boolean isEmpty() {
        return isEmpty || content == null;
    }

    public void clear() {
        content = null;
        this.isEmpty = true;
    }

    public String toString() {
        return content.getEnglishOrFrench(getLanguage());
    }
}
