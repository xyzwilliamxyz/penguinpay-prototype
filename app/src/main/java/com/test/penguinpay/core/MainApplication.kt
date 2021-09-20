package com.test.penguinpay.core

import android.app.Application
import com.test.penguinpay.di.mapperModules
import com.test.penguinpay.di.networkModules
import com.test.penguinpay.di.repositoryModules
import com.test.penguinpay.di.routerModules
import com.test.penguinpay.di.useCaseModules
import com.test.penguinpay.di.validatorModules
import com.test.penguinpay.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                networkModules,
                validatorModules,
                useCaseModules,
                viewModelModules,
                mapperModules,
                repositoryModules,
                routerModules
            )
        }
    }
}