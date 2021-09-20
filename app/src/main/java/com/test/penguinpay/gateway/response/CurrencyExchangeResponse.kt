package com.test.penguinpay.gateway.response

data class CurrencyExchangeResponse(
    val base: String,
    val rates: Map<String, Double>
)
