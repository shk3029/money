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

        long[] moneyArr = new long[count];
        long[] rateArr = new long[count];

        int sum = 0;
        for (int i=0; i<count-1; i++)
        {
            int bound = (int)(money-sum) / 2;

            Random random = new Random();
            rateArr[i] =  bound >= 1 ?  random.nextInt(bound) + 1 : 0l;
            sum += rateArr[i];
        }
        rateArr[count-1] = money-sum;

        return rateArr;
    }
}
