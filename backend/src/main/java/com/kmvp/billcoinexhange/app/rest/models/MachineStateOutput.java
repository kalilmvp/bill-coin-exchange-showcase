package com.kmvp.billcoinexhange.app.rest.models;

import com.kmvp.billcoinexhange.app.models.CoinCount;
import com.kmvp.billcoinexhange.app.models.MachineState;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kalil.peixoto
 * @date 4/4/25 10:19
 * @email kalilmvp@gmail.com
 */
public record MachineStateOutput(int amountInDollars, List<CoinCountOutput> coins, int coinsAmount, int totalAmount) {
    public static MachineStateOutput with(final MachineState state) {
        var coinsOutput = state.coins().stream()
                .map(MachineStateOutput::toCoinCountOutput)
                .toList();
        return new MachineStateOutput(state.amountInDollars(), coinsOutput, state.coinsAmount(), state.totalAmount());
    }

    private static CoinCountOutput toCoinCountOutput(CoinCount coinCount) {
        return CoinCountOutput.with(coinCount.coinCount(), coinCount.coinValue(), coinCount.totalCents());
    }
}
