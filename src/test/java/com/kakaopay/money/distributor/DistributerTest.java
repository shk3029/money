package com.kakaopay.money.distributor;

import com.kakaopay.money.constant.ShareType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class DistributerTest {

    Distributer distributer;


    @Test
    @DisplayName("분배로직 1) 10000원을 3명에게 랜덤으로 분배 (100% 비율을 랜덤으로 3등분하고, 금액에 적용한다")
    void random() {
        distributer = ShareType.RANDOM.getDistributer();
        long[] moneyArr = distributer.distribute(10000l, 3);
        Arrays.stream(moneyArr).asDoubleStream().forEach(num ->
                Assertions.assertTrue(num > 0));
        Assertions.assertEquals(moneyArr.length, 3);
    }



    @Test
    @DisplayName("분배로직 2) 10000원을 3명에게 공정하게 분배, 나머지가 0이 아니면 마지막 사람에게 더해준다")
    void equilty() {
        distributer = ShareType.EQUITY.getDistributer();
        long[] moneyArr = distributer.distribute(10000l, 3);
        long[] result = {3333,3333,3334};
        Assertions.assertArrayEquals(moneyArr, result);
    }



}