package com.kmvp.billcoinexhange.app.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kalil.peixoto
 * @date 4/4/25 17:28
 * @email kalilmvp@gmail.com
 */
public record ExchangeInputRequest(@JsonProperty("value") int value) {
}
