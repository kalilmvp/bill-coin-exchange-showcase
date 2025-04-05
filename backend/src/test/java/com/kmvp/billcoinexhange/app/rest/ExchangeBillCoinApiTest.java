package com.kmvp.billcoinexhange.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmvp.billcoinexhange.app.rest.models.ExchangeInputRequest;
import com.kmvp.billcoinexhange.app.utils.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author kalil.peixoto
 * @date 4/4/25 22:26
 * @email kalilmvp@gmail.com
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExchangeBillCoinApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.initializeCoins();
    }

    private void initializeCoins() throws Exception {
        this.mockMvc.perform(put("/api/bill-coin-exchange/init"))
                .andExpect(status().isOk());
    }

    @Test
    void givenAValidInput_whenCallExchangeCoinLeastFor1Dollar_shouldReturnLeastCoinsExchanged() throws Exception {
        ExchangeInputRequest inputRequest = new ExchangeInputRequest(1);

        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/least")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(inputRequest));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountDollars").value(1))
                .andExpect(jsonPath("$.coins", hasSize(1)))
                .andExpect(jsonPath("$.coins[0].totalCoins", equalTo(4)))
                .andExpect(jsonPath("$.coins[0].cents", equalTo(25)))
                .andExpect(jsonPath("$.coins[0].dollarsCorrespondent", equalTo(1)))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.amountInDollars", equalTo(1)))
                .andExpect(jsonPath("$.state.coinsAmount", equalTo(40)))
                .andExpect(jsonPath("$.state.totalAmount", equalTo(41)))
                .andExpect(jsonPath("$.state.coins", hasSize(4)))
                .andExpect(jsonPath("$.state.coins[0].coinCount", equalTo(96)))
                .andExpect(jsonPath("$.state.coins[0].coinValue", equalTo(25)))
                .andExpect(jsonPath("$.state.coins[0].totalCents", equalTo(2400)));
    }

    @Test
    void givenAnInvalidInput_whenCallExchangeCoinLeast_shouldReturnErrorValueHasToBeNumeric() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/least")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"asd\"}");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_VALUE_HAS_TO_BE_NUM)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
    void givenAnInputEqualsZeroOrLower_whenCallExchangeCoinLeast_shouldReturnErrorValueGreaterThan() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/least")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(0)));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_VALUE_GREATER_THAN_0)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
    void givenAValidInput_whenCallExchangeCoinLeastFor30DollarAndThen20_shouldReturnErrorNotEnoughCoins() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/least")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(30)));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountDollars").value(30))
                .andExpect(jsonPath("$.coins", hasSize(2)))
                .andExpect(jsonPath("$.coins[0].totalCoins", equalTo(100)))
                .andExpect(jsonPath("$.coins[0].cents", equalTo(25)))
                .andExpect(jsonPath("$.coins[0].dollarsCorrespondent", equalTo(25)))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.amountInDollars", equalTo(30)))
                .andExpect(jsonPath("$.state.coinsAmount", equalTo(11)))
                .andExpect(jsonPath("$.state.totalAmount", equalTo(41)))
                .andExpect(jsonPath("$.state.coins", hasSize(3)))
                .andExpect(jsonPath("$.state.coins[0].coinCount", equalTo(50)))
                .andExpect(jsonPath("$.state.coins[0].coinValue", equalTo(10)))
                .andExpect(jsonPath("$.state.coins[0].totalCents", equalTo(500)));

        final MockHttpServletRequestBuilder request1 = post("/api/bill-coin-exchange/least")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(20)));

        this.mockMvc.perform(request1)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_NOT_ENOUG_COINS)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
    void givenAValidInput_whenCallExchangeCoinMostFor1Dollar_shouldReturnMostCoinsExchanged() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(1)));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountDollars").value(1))
                .andExpect(jsonPath("$.coins", hasSize(1)))
                .andExpect(jsonPath("$.coins[0].totalCoins", equalTo(100)))
                .andExpect(jsonPath("$.coins[0].cents", equalTo(1)))
                .andExpect(jsonPath("$.coins[0].dollarsCorrespondent", equalTo(1)))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.amountInDollars", equalTo(1)))
                .andExpect(jsonPath("$.state.coinsAmount", equalTo(41)))
                .andExpect(jsonPath("$.state.totalAmount", equalTo(42)))
                .andExpect(jsonPath("$.state.coins", hasSize(4)))
                .andExpect(jsonPath("$.state.coins[0].coinCount", equalTo(100)))
                .andExpect(jsonPath("$.state.coins[0].coinValue", equalTo(25)))
                .andExpect(jsonPath("$.state.coins[0].totalCents", equalTo(2500)));
    }

    @Test
    void givenAnInputEqualsZeroOrLower_whenCallExchangeCoinMost_shouldReturnErrorValueGreaterThan() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(0)));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_VALUE_GREATER_THAN_0)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
    void givenAnInvalidInput_whenCallExchangeCoinMost_shouldReturnErrorValueHasToBeNumeric() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"asd\"}");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_VALUE_HAS_TO_BE_NUM)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
     void givenAValidInput_whenCallExchangeCoinMostFor40DollarAndThen20_shouldReturnErrorNotEnoughCoins() throws Exception {
        final MockHttpServletRequestBuilder request = post("/api/bill-coin-exchange/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(40)));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountDollars").value(40))
                .andExpect(jsonPath("$.coins", hasSize(4)))
                .andExpect(jsonPath("$.coins[0].totalCoins", equalTo(100)))
                .andExpect(jsonPath("$.coins[0].cents", equalTo(1)))
                .andExpect(jsonPath("$.coins[0].dollarsCorrespondent", equalTo(1)))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.amountInDollars", equalTo(40)))
                .andExpect(jsonPath("$.state.coinsAmount", equalTo(41)))
                .andExpect(jsonPath("$.state.totalAmount", equalTo(81)))
                .andExpect(jsonPath("$.state.coins", hasSize(4)))
                .andExpect(jsonPath("$.state.coins[0].coinCount", equalTo(100)))
                .andExpect(jsonPath("$.state.coins[0].coinValue", equalTo(25)))
                .andExpect(jsonPath("$.state.coins[0].totalCents", equalTo(2500)));

        final MockHttpServletRequestBuilder request1 = post("/api/bill-coin-exchange/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ExchangeInputRequest(20)));

        this.mockMvc.perform(request1)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", equalTo(MessageUtils.MSG_NOT_ENOUG_COINS)))
                .andExpect(jsonPath("$.details", notNullValue()));
    }

    @Test
    void givenNoCommand_whenCallInitialize_shouldInitializeCoins() throws Exception {
        this.initializeCoins();
    }

}
