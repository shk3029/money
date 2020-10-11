package com.kakaopay.money.distributor;

import java.util.Random;

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
        final int MAX_RATE = 100;

        long[] moneyArr = new long[count];
        int[] rateArr = new int[count];

        int sum = 0;
        for (int i=0; i<count-1; i++)
        {
            Random random = new Random();
            rateArr[i] = random.nextInt((MAX_RATE - sum) / 2) + 1;
            sum += rateArr[i];
        }
        rateArr[count-1] = MAX_RATE-sum;

        long charge = money;
        for (int i=0; i<rateArr.length-1; i++) {
            long temp = (long)((double) money * ((double)(rateArr[i]) / (double)100));
            moneyArr[i] = temp;
            charge = charge - temp;
        }
        moneyArr[rateArr.length-1] = charge;

        return moneyArr;
    }
}
