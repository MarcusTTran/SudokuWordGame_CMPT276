package com.echo.wordsudoku.models.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

    // generate a random number given max value i
    // @param int num which is number of wordPairs requested
    // @param int max which is the max value of the random number (inclusive)
    // @param int min which is the min value of the random number (inclusive)
    // @return WordPair[] which is an array of WordPair objects
    public static List<Integer> generateRandomIndexes(int size, int max, int min) {
        // initialize Random object
        Random random = new Random();
        // make an result array to collect the random indexes
        ArrayList<Integer> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            // get a random number ranged from min to max (inclusive)
            int randomNumber = random.nextInt(max-min+1) + min;
            // if the random number is picked before
            while (result.contains(randomNumber)) {
                // try another random number
                randomNumber = random.nextInt(max-min+1) + min;
            }

            // since randomNumber is not repeated, assign it to the ith index of the result
            result.add(randomNumber);
        }
        // return the random indexes as int[]
        return result;
    }
}
