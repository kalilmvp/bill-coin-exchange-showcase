package com.kmvp.billcoinexhange.app.rest;

import com.kmvp.billcoinexhange.app.rest.models.ExchangeInputRequest;
import com.kmvp.billcoinexhange.app.rest.models.ExchangeOutput;
import com.kmvp.billcoinexhange.app.services.ExchangeBillCoin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kalil.peixoto
 * @date 4/4/25 17:29
 * @email kalilmvp@gmail.com
 */
@RestController
public class ExchangeBillCoinApiImpl implements ExchangeBillCoinApi {
    private final ExchangeBillCoin exchangeBillCoinLeast;
    private final ExchangeBillCoin exchangeBillCoinMost;

    public ExchangeBillCoinApiImpl(@Qualifier("exchangeBillCoinLeast") final ExchangeBillCoin exchangeBillCoinLeast
            , @Qualifier("exchangeBillCoinMost") final ExchangeBillCoin exchangeBillCoinMost) {
        this.exchangeBillCoinLeast = exchangeBillCoinLeast;
        this.exchangeBillCoinMost = exchangeBillCoinMost;
    }

    @Override
    public ResponseEntity<?> exchangeLeast(ExchangeInputRequest input) {
        return ResponseEntity.ok(ExchangeOutput.from(
                input.value(),
                this.exchangeBillCoinLeast.execute(input.value()),
                this.exchangeBillCoinLeast.getState(input.value())
        ));
    }

    @Override
    public ResponseEntity<?> exchangeMost(ExchangeInputRequest input) {
        return ResponseEntity.ok(ExchangeOutput.from(
                input.value(),
                this.exchangeBillCoinMost.execute(input.value()),
                this.exchangeBillCoinLeast.getState(input.value())
        ));
    }

    @Override
    public ResponseEntity<?> initialize() {
        this.exchangeBillCoinLeast.initializeCoins();
        this.exchangeBillCoinMost.initializeCoins();
        return ResponseEntity.ok().build();
    }
}
