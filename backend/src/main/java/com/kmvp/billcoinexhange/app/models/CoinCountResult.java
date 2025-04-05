package com.kmvp.billcoinexhange.app.models;

import java.util.List;

/**
 * @author kalil.peixoto
 * @date 4/4/25 15:30
 * @email kalilmvp@gmail.com
 */
public record CoinCountResult(int totalCoinsAmount, List<CoinCount> coinsCount) {
    public static CoinCountResult with(final int totalCoinsAmount, final List<CoinCount> coinsCount) {
        return new CoinCountResult(totalCoinsAmount, coinsCount);
    }
}
