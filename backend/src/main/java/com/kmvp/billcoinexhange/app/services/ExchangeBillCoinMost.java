package com.kmvp.billcoinexhange.app.services;

import com.kmvp.billcoinexhange.app.exceptions.InvalidValueException;
import com.kmvp.billcoinexhange.app.exceptions.NotEnoughCoinsException;
import com.kmvp.billcoinexhange.app.models.MachineState;
import com.kmvp.billcoinexhange.app.utils.ExchangeUtils;
import com.kmvp.billcoinexhange.app.utils.MessageUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author kalil.peixoto
 * @date 4/4/25 10:28
 * @email kalilmvp@gmail.com
 */
@Service("exchangeBillCoinMost")
public class ExchangeBillCoinMost implements ExchangeBillCoin {
    private Map<Integer, Integer> coins;

    @PostConstruct
    public void init() {
        this.initializeCoins();
    }

    private void reverseArray(int[] array) {
        for (int left = 0, right = array.length - 1; left < right; left++, right--) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
        }
    }

    @Override
    public Map<Integer, Integer> execute(int amountDollars) {
        if (amountDollars <= 0) {
            throw new InvalidValueException(MessageUtils.MSG_VALUE_GREATER_THAN_0);
        }

        int amountInCents = amountDollars * 100;
        Map<Integer, Integer> result = new HashMap<>();

        int[] coinValues = ExchangeUtils.COIN_VALUES.clone();

        reverseArray(coinValues);

        for (int value : coinValues) {
            int count = Math.min(amountInCents / value, this.getCoins().get(value));
            if (count > 0) {
                result.put(value, count);
                amountInCents -= count * value;
                this.coins.put(value, this.getCoins().get(value) - count);
            }
        }
        if (amountInCents > 0) {
            throw new NotEnoughCoinsException(MessageUtils.MSG_NOT_ENOUG_COINS);
        }
        return result;
    }

    @Override
    public MachineState getState(int amountDollars) {
        var coinCountResult = ExchangeUtils.calculaCoinsCount(this.getCoins());
        return MachineState.with(amountDollars,
                coinCountResult.coinsCount(),
                coinCountResult.totalCoinsAmount() / 100,
                (amountDollars + coinCountResult.totalCoinsAmount() / 100));
    }

    @Override
    public void initializeCoins() {
        this.coins = new HashMap<>();
        for (int value : ExchangeUtils.COIN_VALUES) {
            this.coins.put(value, 100);
        }
    }

    public Map<Integer, Integer> getCoins() {
        return coins;
    }
}
