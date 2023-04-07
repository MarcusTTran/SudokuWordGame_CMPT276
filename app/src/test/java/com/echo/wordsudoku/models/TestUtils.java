package com.echo.wordsudoku.models;

import com.echo.wordsudoku.models.utility.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestUtils {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzéèêëàâîïôûùç";
    private static final Random RANDOM = new Random();
    public static String makeRandomEnglishWord(int length) {
        String word = "";
        for (int i = 0; i < length; i++) {
            word += (char) (Math.random() * 26 + 'a');
        }
        return word;
    }

    public static String makeRandomEnglishWord() {
        return makeRandomEnglishWord((int) (Math.random() * 12 + 1));
    }

    public static String makeRandomFrenchWord(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            char c = LETTERS.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    public static String makeRandomFrenchWord() {
        return makeRandomFrenchWord((int) (Math.random() * 12 + 1));
    }

    public static  <T> List<T> getRandomElements(List<T> list, int numberOfElements) {
        List<T> randomElements = new ArrayList<>();
        for (int i = 0; i < numberOfElements; i++) {
            randomElements.add(list.get(MathUtils.getRandomNumberBetweenIncluding(0, list.size() - 1)));
        }
        return randomElements;
    }

    public static int getRandomIntElement(int[] array) {
        return array[(int) (Math.random() * array.length)];
    }

    public static List<Integer> makeListOfPuzzleSizeInt(int size) {
        List<Integer> puzzleSizeIntList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            puzzleSizeIntList.add(i);
        }
        return puzzleSizeIntList;
    }
}
