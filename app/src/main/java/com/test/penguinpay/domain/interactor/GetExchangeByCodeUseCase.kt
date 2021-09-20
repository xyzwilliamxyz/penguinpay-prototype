package com.test.penguinpay.domain.interactor

import android.util.Log
import com.test.penguinpay.domain.model.CurrencyExchange
import com.test.penguinpay.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class GetExchangeByCodeUseCase(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: CurrencyExchangeRepository
) {

    suspend fun execute(currencyCode: String): CurrencyExchange? = withContext(coroutineDispatcher) {
        return@withContext try {
            val result = repository.getCurrencies()
            result[currencyCode]
        } catch (ex: Exception) {
            Log.e(this.javaClass.name, LOG_MSG, ex)
            null
        }
    }

    companion object {
        private const val LOG_MSG = "error fetching exchange code"
    }
}