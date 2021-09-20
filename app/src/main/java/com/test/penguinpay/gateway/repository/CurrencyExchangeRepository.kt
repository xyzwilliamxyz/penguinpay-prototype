package com.test.penguinpay.gateway.repository

import com.test.penguinpay.domain.model.CurrencyExchange
import com.test.penguinpay.gateway.api.CurrencyExchangeApi
import com.test.penguinpay.gateway.mapper.CurrencyExchangeMapper

class CurrencyExchangeRepository(
    private val mapper: CurrencyExchangeMapper,
    private val api: CurrencyExchangeApi
) {

    suspend fun getCurrencies(): Map<String, CurrencyExchange> {
        val response = api.getLatest()
        return mapper.toCurrency(response.rates)
    }
}