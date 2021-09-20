package com.test.penguinpay.domain.enums

enum class CountryEnum(
    val currencyCode: String,
    val countryName: String,
    val phonePrefix: String,
    val phoneDigits: Int
) {
    NONE("", "", "---", 9),
    KENYA("KES", "Kenya", "+254", 9),
    NIGERIA("NGN", "Nigeria", "+234", 7),
    TANZANIA("TZS", "Tanzania", "+255", 9),
    UGANDA("UGX", "Uganda", "+256", 7);

    companion object {
        private val prefixes = values()

        fun getCountryByPrefix(number: String) =
            prefixes.firstOrNull { number.startsWith(it.phonePrefix) } ?: NONE

    }
}