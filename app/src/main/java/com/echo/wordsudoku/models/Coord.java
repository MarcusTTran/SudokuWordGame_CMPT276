package com.echo.wordsudoku.models;

import java.util.Objects;

public class Coord {

    WordPair wordPair;
    int x;
    int y;

    public Coord(int x, int y, WordPair wordPair) {
        this.x = x;
        this.y = y;
        this.wordPair = wordPair;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public WordPair getWordPair() { return wordPair; }
    public void setWordPair(WordPair wordPair) { this.wordPair = wordPair; }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
