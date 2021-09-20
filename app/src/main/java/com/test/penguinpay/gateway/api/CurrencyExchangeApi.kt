package com.test.penguinpay.gateway.api

import com.test.penguinpay.gateway.response.CurrencyExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeApi {

    @GET("latest.json")
    suspend fun getLatest(
        @Query("currencyCode") currencyCode: String = "USD"
    ): CurrencyExchangeResponse
}