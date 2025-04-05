package com.kmvp.billcoinexhange.app.models;

import java.util.List;

/**
 * @author kalil.peixoto
 * @date 4/4/25 10:19
 * @email kalilmvp@gmail.com
 */
public record MachineState(int amountInDollars, List<CoinCount> coins, int coinsAmount, int totalAmount) {
    public static MachineState with(int amountInDollars, List<CoinCount> coins, int coinsAmount, int totalAmount) {
        return new MachineState(amountInDollars, coins, coinsAmount, totalAmount);
    }
}
