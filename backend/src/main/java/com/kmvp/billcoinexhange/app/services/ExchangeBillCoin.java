package com.kmvp.billcoinexhange.app.services;

import com.kmvp.billcoinexhange.app.models.MachineState;

import java.util.Map;

/**
 * @author kalil.peixoto
 * @date 4/2/25 15:29
 * @email kalilmvp@gmail.com
 */
public interface ExchangeBillCoin {
    Map<Integer, Integer> execute(int amountDollars);
    MachineState getState(int amountDollars);
    void initializeCoins();
}
