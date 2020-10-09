package com.kakaopay.money.distributor;

import com.kakaopay.money.constant.ShareType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DistributerTest {

    Distributer distributer;

    @Test
    @DisplayName("분배로직 1) 10000원을 3명에게 랜덤으로 분배")
    void randomSharer() {
        distributer = ShareType.RANDOM.getDistributer();
        distributer.distribute(10000l, 3);
    }

    @Test
    @DisplayName("분배로직 2) 10000원을 3명에게 공정하게 분배, 나머지가 0이 아니면 마지막 사람에게 더해준다")
    void equiltySharer() {
        long[] moneyArr = distributer.distribute(10000l, 3);
        distributer = ShareType.EQUITY.getDistributer();
        long[] result = {3333,3333,3334};
        Assertions.assertArrayEquals(moneyArr, result);
    }

}