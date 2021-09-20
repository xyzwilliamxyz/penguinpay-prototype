package com.test.penguinpay.di

import com.test.penguinpay.domain.interactor.GetExchangeByCodeUseCase
import com.test.penguinpay.domain.validator.BinaryValidator
import com.test.penguinpay.domain.validator.PhoneNumberValidator
import com.test.penguinpay.gateway.api.CurrencyExchangeApi
import com.test.penguinpay.gateway.config.AuthInterceptor
import com.test.penguinpay.gateway.config.RetrofitConfig
import com.test.penguinpay.gateway.mapper.CurrencyExchangeMapper
import com.test.penguinpay.gateway.repository.CurrencyExchangeRepository
import com.test.penguinpay.router.TransactionRouter
import com.test.penguinpay.ui.sendtransaction.SendTransactionViewModel
import com.test.penguinpay.ui.sendtransactionsuccess.SendTransactionSuccessViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModules = module {
    viewModel {
        SendTransactionViewModel(
            context = androidContext(),
            useCase = get(),
            phoneNumberValidator = get(),
            binaryValidator = get(),
            router = get()
        )
    }
    viewModel { SendTransactionSuccessViewModel(get()) }
}

val useCaseModules = module {
    factory { GetExchangeByCodeUseCase(repository = get()) }
}

val validatorModules = module {
    factory { PhoneNumberValidator() }
    factory { BinaryValidator() }
}

val networkModules = module {
    factory { AuthInterceptor() }
    factory { RetrofitConfig.provideOkHttpClient(get()) }
    single { RetrofitConfig.provideRetrofit(get()) }

    // APIs
    factory { get<Retrofit>().create    (CurrencyExchangeApi::class.java) }
}

val mapperModules = module {
    factory { CurrencyExchangeMapper() }
}

val repositoryModules = module {
    factory { CurrencyExchangeRepository(get(), get()) }
}

val routerModules = module {
    factory { TransactionRouter() }
}