package com.test.penguinpay.router

import android.content.Context
import android.content.Intent
import com.test.penguinpay.ui.sendtransaction.SendTransactionActivity
import com.test.penguinpay.ui.sendtransactionsuccess.SendTransactionSuccessActivity

class TransactionRouter {

    fun openTransactionSuccessScreen(context: Context, userFirstName: String) {
        context.startActivity(
            SendTransactionSuccessActivity.getIntent(context, userFirstName)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        )
    }

    fun openSendTransactionScreen(context: Context) {
        context.startActivity(
            SendTransactionActivity.getIntent(context)
        )
    }
}