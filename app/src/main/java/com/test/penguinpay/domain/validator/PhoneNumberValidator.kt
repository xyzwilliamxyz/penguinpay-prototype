package com.test.penguinpay.domain.validator

import androidx.core.text.isDigitsOnly
import com.test.penguinpay.domain.enums.CountryEnum

class PhoneNumberValidator {

    fun isPhoneValid(number: String): Boolean {
        if (!isDigitsOnly(number)) {
            return false
        }

        val country = CountryEnum.getCountryByPrefix(number)
        return if (country == CountryEnum.NONE) {
            false
        } else number.length - COUNTRY_CODE_DIGITS == country.phoneDigits
    }

    private fun isDigitsOnly(number: String): Boolean {
        return number.removePrefix(PLUS_SIGN).isDigitsOnly()
    }

    companion object {
        private const val COUNTRY_CODE_DIGITS = 4
        private const val PLUS_SIGN = "+"
    }
}