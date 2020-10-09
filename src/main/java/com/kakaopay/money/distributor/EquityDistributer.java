package com.kakaopay.money.distributor;

public class EquityDistributer implements Distributer {

    private EquityDistributer() {
    }

    private static class LazyHolder {
        private static final EquityDistributer INSTANCE = new EquityDistributer();
    }

    public static EquityDistributer getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public long[] distribute(long total, int count) {

        long[] moneyArr = new long[count];
        long shared = 0l;
        long divied = total / count;

        for (int i=0; i < count-1; i++) {
            moneyArr[i] = divied;
            shared += divied;
        }
        moneyArr[count-1] = total - shared;
        return moneyArr;
    }
}
