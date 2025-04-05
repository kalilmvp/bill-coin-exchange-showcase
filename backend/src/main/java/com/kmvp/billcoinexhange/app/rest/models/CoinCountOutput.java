package com.kmvp.billcoinexhange.app.rest.models;

/**
 * @author kalil.peixoto
 * @date 4/4/25 10:21
 * @email kalilmvp@gmail.com
 */
public record CoinCountOutput(int coinCount, int coinValue, int totalCents) {
    public static CoinCountOutput with(int coinCount, int coinValue, int totalCents) {
        return new CoinCountOutput(coinCount, coinValue, totalCents);
    }
}
