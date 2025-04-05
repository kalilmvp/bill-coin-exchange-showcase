package com.kmvp.billcoinexhange.app.rest.models;

import com.kmvp.billcoinexhange.app.models.MachineState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kalil.peixoto
 * @date 4/4/25 17:47
 * @email kalilmvp@gmail.com
 */
public record ExchangeOutput(int amountDollars, List<Coin> coins, MachineStateOutput state) {
    private record Coin(int totalCoins, int cents, int dollarsCorrespondent) {
        public static Coin with(int totalCoins, int cents, int dollarsCorrespondent) {
            return new Coin(totalCoins, cents, dollarsCorrespondent);
        }
    }

    public static ExchangeOutput from(final int amountDollars,
                                      final Map<Integer, Integer> coinCents,
                                      final MachineState state) {

        List<Coin> coins = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : coinCents.entrySet()) {
            coins.add(Coin.with(entry.getValue(), entry.getKey(), Math.divideExact(Math.multiplyExact(entry.getValue(), entry.getKey()), 100)));
        }
        return new ExchangeOutput(amountDollars, coins, MachineStateOutput.with(state));
    }
}
