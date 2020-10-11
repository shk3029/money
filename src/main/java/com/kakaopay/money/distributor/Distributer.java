package com.kakaopay.money.distributor;

public interface Distributer {

    /**
     * 금액을 뿌리는 방식을 구현한다
     * @param money (금액)
     * @param count (인원)
     * @return
     */
    long[] distribute(long money, int count);
}
