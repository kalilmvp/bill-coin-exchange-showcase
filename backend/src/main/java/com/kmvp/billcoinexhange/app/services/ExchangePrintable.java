package com.kmvp.billcoinexhange.app.services;

import java.util.Map;

/**
 * @author kalil.peixoto
 * @date 4/2/25 15:29
 * @email kalilmvp@gmail.com
 */
public interface ExchangePrintable {
    Map<Integer, Integer> execute(int amountDollars, boolean least);
    void printState(int amountDollars);
}
