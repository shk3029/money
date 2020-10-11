package com.kakaopay.money.distributor;

import java.util.Arrays;

public class RandomDistributer implements Distributer {

    private RandomDistributer() {
    }

    private static class LazyHolder {
        private static final RandomDistributer INSTANCE = new RandomDistributer();
    }

    public static RandomDistributer getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public long[] distribute(long money, int count) {

        long tatal = 0;

        int[] ints = randomList(count, money);

        for (int i=0; i<ints.length; i++) {
            tatal+=ints[i];
            System.out.println(ints[i]);
        }
        System.out.println(tatal);

        return new long[0];
    }

    static int[] randomList(int m, long n)
    {

        // Create an array of size m where
        // every element is initialized to 0
        int arr[] = new int[m];

        // To make the sum of the final list as n
        for (int i = 0; i < n; i++)
        {

            // Increment any random element
            // from the array by 1
            arr[(int)(Math.random() * m)]++;
        }

        return arr;
    }



}
