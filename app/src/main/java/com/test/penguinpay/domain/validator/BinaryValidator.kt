package com.test.penguinpay.domain.validator

class BinaryValidator {

    fun isDecimalBinaryNumberValid(number: String): Boolean {
        return number.replace(POINT, "").let { REGEX_BINARY.toRegex().matches(it) }
    }

    fun isDecimalBinaryNumberWithCurrencyValid(number: String): Boolean {
        val binary = number.split(" ").getOrNull(1)
        return binary?.let { isDecimalBinaryNumberValid(it) } ?: false
    }

    companion object {
        private const val REGEX_BINARY = "[0-1]+"
        private const val POINT = "."
    }
}