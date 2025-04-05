package com.kmvp.billcoinexhange.app.utils;

import com.kmvp.billcoinexhange.app.models.CoinCount;
import com.kmvp.billcoinexhange.app.models.CoinCountResult;
import com.kmvp.billcoinexhange.app.services.ExchangeBillCoinLeast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author kalil.peixoto
 * @date 4/4/25 11:18
 * @email kalilmvp@gmail.com
 */
public class ExchangeUtils {
    private ExchangeUtils() {}

    public static final int[] COIN_VALUES = {25, 10, 5, 1};

    public static CoinCountResult calculaCoinsCount(final Map<Integer, Integer> coins) {
        int totalCoinsAmount = 0;
        final List<CoinCount> coinsCount = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : coins.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .filter(c -> c.getValue() > 0)
                .toList()) {
            int coinValue = entry.getKey();
            int coinCount = entry.getValue();
            totalCoinsAmount += coinValue * coinCount;
            coinsCount.add(CoinCount.with(entry.getValue(), entry.getKey(), coinValue * coinCount));
        }
        return CoinCountResult.with(totalCoinsAmount, coinsCount);
    }
}
