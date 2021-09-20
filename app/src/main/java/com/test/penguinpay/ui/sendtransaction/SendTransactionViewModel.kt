package com.test.penguinpay.ui.sendtransaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.penguinpay.R
import com.test.penguinpay.core.fromBinaryToDouble
import com.test.penguinpay.core.fromDoubleToBinary
import com.test.penguinpay.domain.enums.CountryEnum
import com.test.penguinpay.domain.interactor.GetExchangeByCodeUseCase
import com.test.penguinpay.domain.model.CurrencyExchange
import com.test.penguinpay.domain.validator.BinaryValidator
import com.test.penguinpay.domain.validator.PhoneNumberValidator
import com.test.penguinpay.router.TransactionRouter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SendTransactionViewModel(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val context: Context,
    private val useCase: GetExchangeByCodeUseCase,
    private val phoneNumberValidator: PhoneNumberValidator,
    private val binaryValidator: BinaryValidator,
    private val router: TransactionRouter
): ViewModel() {

    private lateinit var exchangeJob: Job

    private val firstName = MutableLiveData("")
    private val lastName = MutableLiveData("")
    private val phoneNumber = MutableLiveData("")
    private val transferValue = MutableLiveData("")

    private val _isFormValid = MediatorLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    private val _isPhoneValid = MediatorLiveData<Boolean>()
    val isPhoneValid: LiveData<Boolean> = _isPhoneValid

    private val _convertedTransferValue = MutableLiveData<String>()
    val convertedTransferValue: LiveData<String> = _convertedTransferValue

    init {
        _isFormValid.addSource(firstName) { _isFormValid.value = isFormValid() }
        _isFormValid.addSource(lastName) { _isFormValid.value = isFormValid() }
        _isFormValid.addSource(phoneNumber) { _isFormValid.value = isFormValid() }
        _isFormValid.addSource(transferValue) { _isFormValid.value = isFormValid() }
        _isFormValid.addSource(convertedTransferValue) { _isFormValid.value = isFormValid() }

        _isPhoneValid.addSource(phoneNumber) { _isPhoneValid.value = phoneNumberValidator.isPhoneValid(it) }
    }

    fun setFirstName(firstName: String) {
        this.firstName.value = firstName
    }

    fun setLastName(lastName: String) {
        this.lastName.value = lastName
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber.value = phoneNumber
        if (CountryEnum.getCountryByPrefix(phoneNumber) == CountryEnum.NONE) {
            transferValue.value = ""
            _convertedTransferValue.value = ""
        }
    }

    fun setTransferValue(transferValue: String) {
        this.transferValue.value = transferValue
        setConvertedTransferValue(transferValue)
    }

    fun sendTransaction(context: Context) = viewModelScope.launch(coroutineDispatcher) {
        delay(FICTITIOUS_API_DELAY)
        firstName.value?.let {
            router.openTransactionSuccessScreen(context, it)
        }
    }

    private fun setConvertedTransferValue(transferValue: String) {
        val isTransferValueValid = binaryValidator.isDecimalBinaryNumberValid(transferValue)
        if (isTransferValueValid) {
            _convertedTransferValue.value = context.getString(R.string.msg_loading_recipient)
        } else if (transferValue == "") {
            _convertedTransferValue.value = ""
        } else {
            _convertedTransferValue.value = context.getString(R.string.msg_invalid_binary)
        }

        if (::exchangeJob.isInitialized && exchangeJob.isActive) {
            exchangeJob.cancel()
        }

        if (isTransferValueValid) {
            fetchExchange(transferValue)
        }
    }

    private fun fetchExchange(transferValue: String) {
        exchangeJob = viewModelScope.launch(coroutineDispatcher) {
            delay(TIME_TO_PERFORM_API_CALL) // time to performance api call after typing

            val result = useCase.execute(CountryEnum.getCountryByPrefix(phoneNumber.value ?: "").currencyCode)

            if (result == null) {
                _convertedTransferValue.value = context.getString(R.string.msg_error_fetching)
            } else {
                _convertedTransferValue.value = applyCurrency(transferValue, result)
            }
        }
    }

    private fun applyCurrency(transferValue: String, currencyExchange: CurrencyExchange): String {
        return CONVERTED_TRANSFER_FORMAT.format(
            currencyExchange.currencyCode,
            (transferValue.fromBinaryToDouble() * currencyExchange.value).fromDoubleToBinary()
        )
    }

    private fun isFormValid(): Boolean {
        return firstName.value?.trim()?.isNotEmpty() == true
                && lastName.value?.trim()?.isNotEmpty() == true
                && phoneNumberValidator.isPhoneValid(phoneNumber.value ?: "")
                && transferValue.value?.trim()?.isNotEmpty() == true
                && transferValue.value?.let { binaryValidator.isDecimalBinaryNumberValid(it) } == true
                && convertedTransferValue.value?.let { binaryValidator.isDecimalBinaryNumberWithCurrencyValid(it) } == true
    }

    companion object {
        private const val CONVERTED_TRANSFER_FORMAT = "%s %s"
        private const val TIME_TO_PERFORM_API_CALL = 3000L
        private const val FICTITIOUS_API_DELAY = 2000L
    }
}