package com.kmvp.billcoinexhange.app.rest;

import com.kmvp.billcoinexhange.app.rest.models.ExchangeInputRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kalil.peixoto
 * @date 4/2/25 15:30
 * @email kalilmvp@gmail.com
 */
@RequestMapping("/api/bill-coin-exchange")
@Tag(name = "ExchangeBillCoin")
public interface ExchangeBillCoinApi {
    @PostMapping(
            value = "least",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Exchange dollars for cents ( least quantity )")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchanged successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "A validation error"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    ResponseEntity<?> exchangeLeast(@RequestBody ExchangeInputRequest input);

    @PostMapping(
            value = "most",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Exchange dollars for cents ( most quantity)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchanged successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "A validation error"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    ResponseEntity<?> exchangeMost(@RequestBody ExchangeInputRequest input);

    @PutMapping(
            value = "init",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Initialize coins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation succesc"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    ResponseEntity<?> initialize();
}
