package com.kmvp.billcoinexhange.app.services;

import com.kmvp.billcoinexhange.app.exceptions.InvalidValueException;
import com.kmvp.billcoinexhange.app.exceptions.NotEnoughCoinsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeBillCoinTest {

    @Autowired
    @Qualifier("exchangeBillCoinLeast")
    private ExchangeBillCoin exchangeBillCoinLeast;

    @Autowired
    @Qualifier("exchangeBillCoinMost")
    private ExchangeBillCoin exchangeBillCoinMost;

    @BeforeEach
    void setUp() {
        this.exchangeBillCoinLeast.initializeCoins();
        this.exchangeBillCoinMost.initializeCoins();
    }

    @Test
    void given1Dollar_whenCallExchangeLeast_shouldReturn4Coins25Cents() {
        int amountDollar = 1;
        Map<Integer, Integer> result = this.exchangeBillCoinLeast.execute(amountDollar);
        assertEquals(4, result.get(25));
        assertFalse(result.containsKey(10));
        assertFalse(result.containsKey(5));
        assertFalse(result.containsKey(1));
    }

    @Test
    void given1Dollar_whenCallExchangeMost_shouldReturn100Coins1Cent() {
        int amountDollar = 1;
        Map<Integer, Integer> result = this.exchangeBillCoinMost.execute(amountDollar);
        assertEquals(100, result.get(1));
        assertFalse(result.containsKey(25));
        assertFalse(result.containsKey(10));
        assertFalse(result.containsKey(5));
    }

    @Test
    void givenMultipleDollarCoins_whenCallExchangeLeast_shouldThrowErrorNotEnoughCoinsException() {
        assertThrows(NotEnoughCoinsException.class, () -> this.exchangeBillCoinLeast.execute(1000));
    }

    @Test
    void givenMultipleDollarCoins_whenCallExchangeLeastWith0Value_shouldThrowErrorValueGreaterThan0() {
        assertThrows(InvalidValueException.class, () -> this.exchangeBillCoinLeast.execute(0));
    }

    @Test
    void givenMultipleDollarCoins_whenCallExchangeMost_shouldThrowErrorNotEnoughCoinsException() {
        assertThrows(NotEnoughCoinsException.class, () -> this.exchangeBillCoinMost.execute(1000));
    }

    @Test
    void givenMultipleDollarCoins_whenCallExchangeMostWith0Value_shouldThrowErrorValueGreaterThan0() {
        assertThrows(InvalidValueException.class, () -> this.exchangeBillCoinMost.execute(0));
    }

    @Test
    void given30Dollars_whenCallGetStateFromLeastBillCoin_shouldReturnMachineState() {
        int amountDollar = 30;
        var result = this.exchangeBillCoinLeast.execute(amountDollar);
        assertEquals(100, result.get(25));
        assertEquals(50, result.get(10));
        assertFalse(result.containsKey(5));
        assertFalse(result.containsKey(1));
        var state = this.exchangeBillCoinLeast.getState(amountDollar);
        assertEquals(30, state.amountInDollars());
        assertEquals(11, state.coinsAmount());
        assertEquals(41, state.totalAmount());
        assertEquals(3, state.coins().size());
        assertEquals(50, state.coins().getFirst().coinCount());
        assertEquals(10, state.coins().getFirst().coinValue());
        assertEquals(5, state.coins().getFirst().totalCents() / 100);
        assertEquals(100, state.coins().getLast().coinCount());
        assertEquals(1, state.coins().getLast().coinValue());
        assertEquals(1, state.coins().getLast().totalCents() / 100);

    }

    @Test
    void given10Dollars_whenCallGetStateFromMostBillCoin_shouldReturnMachineState() {
        int amountDollar = 10;
        var result = this.exchangeBillCoinMost.execute(amountDollar);
        assertEquals(100, result.get(1));
        assertEquals(100, result.get(5));
        assertEquals(40, result.get(10));
        assertFalse(result.containsKey(25));
        var state = this.exchangeBillCoinMost.getState(amountDollar);
        assertEquals(10, state.amountInDollars());
        assertEquals(31, state.coinsAmount());
        assertEquals(41, state.totalAmount());
        assertEquals(2, state.coins().size());
        assertEquals(100, state.coins().getFirst().coinCount());
        assertEquals(25, state.coins().getFirst().coinValue());
        assertEquals(25, state.coins().getFirst().totalCents() / 100);
        assertEquals(60, state.coins().getLast().coinCount());
        assertEquals(10, state.coins().getLast().coinValue());
        assertEquals(6, state.coins().getLast().totalCents() / 100);

        int amountDollar1 = 10;
        var result1 = this.exchangeBillCoinMost.execute(amountDollar1);
        assertEquals(16, result1.get(25));
        assertEquals(60, result1.get(10));
        assertFalse(result1.containsKey(5));
        assertFalse(result1.containsKey(1));
        var state1 = this.exchangeBillCoinMost.getState(amountDollar1);
        assertEquals(10, state1.amountInDollars());
        assertEquals(21, state1.coinsAmount());
        assertEquals(31, state1.totalAmount());
        assertEquals(1, state1.coins().size());
        assertEquals(84, state1.coins().getFirst().coinCount());
        assertEquals(25, state1.coins().getFirst().coinValue());
        assertEquals(21, state1.coins().getFirst().totalCents() / 100);
    }
}
