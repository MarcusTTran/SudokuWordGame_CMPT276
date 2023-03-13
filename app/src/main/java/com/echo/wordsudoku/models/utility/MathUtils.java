package com.echo.wordsudoku.models.utility;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {
    public static boolean isPerfectSquare(int n) {
        return Math.sqrt(n) % 1 == 0;
    }

    public static boolean isPrimeNumber(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int getRandomNumberBetweenIncluding(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static int[] getMiddleFactors(int n) {
        int[] factors = new int[2];
        List<Integer> factorsList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
               factorsList.add(i);
            }
        }
        int middleIndex = factorsList.size() / 2;
        factors[0] = factorsList.get(middleIndex - 1);
        factors[1] = factorsList.get(middleIndex);
        return factors;
    }
}
