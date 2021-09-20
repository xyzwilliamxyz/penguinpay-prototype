package com.test.penguinpay.ui.sendtransaction

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import com.test.penguinpay.databinding.ActivitySendTransactionBinding
import com.test.penguinpay.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendTransactionActivity: BaseActivity() {

    private val viewModel: SendTransactionViewModel by viewModel()
    private lateinit var binding: ActivitySendTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservables()
    }

    private fun setupViews() {
        binding.tieFirstName.doOnTextChanged { inputText, _, _, _ ->
            viewModel.setFirstName(inputText.toString())
        }

        binding.tieLastName.doOnTextChanged { inputText, _, _, _ ->
            viewModel.setLastName(inputText.toString())
        }

        binding.tiePhoneNumber.doOnTextChanged { inputText, _, _, _ ->
            viewModel.setPhoneNumber(inputText.toString())
        }

        binding.tieTransferValue.doOnTextChanged { inputText, _, _, _ ->
            viewModel.setTransferValue(inputText.toString())
        }

        bindProgressButton(binding.btSend)
        binding.btSend.setOnClickListener {
            (it as MaterialButton).showProgress {
                progressColor = Color.WHITE
            }
            it.isClickable = false
            viewModel.sendTransaction(this)
        }
    }

    private fun setupObservables() {
        viewModel.isFormValid.observe(this) {
            binding.btSend.isEnabled = it
        }

        viewModel.isPhoneValid.observe(this) {
            binding.tilTransferValue.isEnabled = it
        }

        viewModel.convertedTransferValue.observe(this) {
            binding.tvTransferValueConversion.text = it
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SendTransactionActivity::class.java)
        }
    }
}