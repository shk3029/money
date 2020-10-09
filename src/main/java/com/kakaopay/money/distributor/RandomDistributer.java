package com.kakaopay.money.distributor;

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
        return new long[0];
    }

}
