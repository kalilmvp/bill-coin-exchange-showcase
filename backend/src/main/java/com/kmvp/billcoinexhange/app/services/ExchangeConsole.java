package com.kmvp.billcoinexhange.app.services;

import com.kmvp.billcoinexhange.app.exceptions.NotEnoughCoinsException;
import com.kmvp.billcoinexhange.app.utils.ExchangeUtils;
import com.kmvp.billcoinexhange.app.utils.MessageUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kalil.peixoto
 * @date 4/2/25 15:29
 * @email kalilmvp@gmail.com
 */
public class ExchangeConsole implements ExchangePrintable {
    private final Map<Integer, Integer> coins;

    public ExchangeConsole() {
        this.coins = new HashMap<>();
        for (int value : ExchangeUtils.COIN_VALUES) {
            this.coins.put(value, 100);
        }
    }

    private void reverseArray(int[] array) {
        for (int left = 0, right = array.length - 1; left < right; left++, right--) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
        }
    }

    private Map<Integer, Integer> exchangeCoins(int amountDollars, boolean least) {
        int amountInCents = amountDollars * 100;
        Map<Integer, Integer> result = new HashMap<>();

        int[] coinValues = ExchangeUtils.COIN_VALUES.clone();

        if (!least) {
            reverseArray(coinValues);
        }

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
    public Map<Integer, Integer> execute(int amountDollars, boolean least) {
        return exchangeCoins(amountDollars, least);
    }

    @Override
    public void printState(final int amountInDollars) {
        System.out.println("\nMachine state:");
        System.out.printf("Bills amount: $%s", amountInDollars);
        int totalCoinsAmount = 0;
        final StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : this.getCoins().entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .toList()) {
            int coinValue = entry.getKey();
            int coinCount = entry.getValue();
            totalCoinsAmount += coinValue * coinCount;
            sb.append(String.format("%n\t• %s coins (%s cents) = $%s", coinCount, coinValue, (coinValue * coinCount) / 100));
        }
        System.out.printf("%nCoins amount: $%s", totalCoinsAmount / 100);
        System.out.println(sb);
        System.out.printf("Total amount: $%s", (amountInDollars + totalCoinsAmount / 100));
        System.out.println("\n");
    }

    public Map<Integer, Integer> getCoins() {
        return coins;
    }
}
