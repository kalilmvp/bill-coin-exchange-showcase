package com.kmvp.billcoinexhange.app.models;

/**
 * @author kalil.peixoto
 * @date 4/4/25 10:21
 * @email kalilmvp@gmail.com
 */
public record CoinCount(int coinCount, int coinValue, int totalCents) {
    public static CoinCount with(int coinCount, int coinValue, int totalCents) {
        return new CoinCount(coinCount, coinValue, totalCents);
    }
}
