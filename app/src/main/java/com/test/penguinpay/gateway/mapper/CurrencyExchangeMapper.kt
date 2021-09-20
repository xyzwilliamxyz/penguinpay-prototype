package com.test.penguinpay.gateway.mapper

import com.test.penguinpay.domain.model.CurrencyExchange

class CurrencyExchangeMapper {

    fun toCurrency(response: Map<String, Double>): Map<String, CurrencyExchange> {
        return response.mapValues {
            CurrencyExchange(it.key, it.value)
        }
    }
}