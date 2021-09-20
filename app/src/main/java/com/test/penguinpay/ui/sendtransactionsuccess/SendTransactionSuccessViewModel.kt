package com.test.penguinpay.ui.sendtransactionsuccess

import android.content.Context
import androidx.lifecycle.ViewModel
import com.test.penguinpay.router.TransactionRouter

class SendTransactionSuccessViewModel(
    private val router: TransactionRouter
): ViewModel() {

    fun openNewTransactionScreen(context: Context) {
        router.openSendTransactionScreen(context)
    }
}