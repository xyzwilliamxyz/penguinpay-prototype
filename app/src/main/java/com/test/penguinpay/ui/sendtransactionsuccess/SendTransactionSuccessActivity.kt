package com.test.penguinpay.ui.sendtransactionsuccess

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.test.penguinpay.R
import com.test.penguinpay.databinding.ActivitySendTransactionSuccessBinding
import com.test.penguinpay.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendTransactionSuccessActivity: BaseActivity() {

    private val viewModel: SendTransactionSuccessViewModel by viewModel()
    private lateinit var binding: ActivitySendTransactionSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendTransactionSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.tvSuccessMessage.text = getString(
            R.string.msg_transaction_success,
            intent.getStringExtra(ARG_USER_FIRST_NAME)
        )

        binding.btNewTransaction.setOnClickListener {
            viewModel.openNewTransactionScreen(this)
        }
    }

    companion object {
        private const val ARG_USER_FIRST_NAME = "ARG_USER_FIRST_NAME"

        fun getIntent(context: Context, userFirstName: String): Intent {
            return Intent(context, SendTransactionSuccessActivity::class.java).apply {
                putExtra(ARG_USER_FIRST_NAME, userFirstName)
            }
        }
    }
}