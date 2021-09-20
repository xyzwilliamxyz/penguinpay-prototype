package com.test.penguinpay.core

import kotlin.math.pow

fun String.fromBinaryToDouble(): Double {
    val parts = this.split(POINT)
    val result = Integer.parseInt(parts[0], 2).toDouble()
    var decimalPlaces = 0.0
    if (parts.size > 1) {
        for (i in 1..parts[1].length) {
            val digit = parts[1][i - 1].digitToInt()
            if (digit == 1) {
                decimalPlaces += digit.times(2).toDouble().pow(-i)
            }
        }
    }
    return result + decimalPlaces
}

fun Double.fromDoubleToBinary(): String {
    val firstPart = Integer.toBinaryString(this.toInt())
    val secondPart = getDecimalPlacesInBinary(this)

    return "$firstPart.$secondPart"
}

private fun getDecimalPlacesInBinary(value: Double): String {
    var fractional = (value - value.toInt())
    val decimalPlaces = StringBuilder()
    var precision = 4
    while (precision-- > 0) {
        fractional *= 2
        val fractionBit: Int = fractional.toInt()
        if (fractionBit == 1) {
            fractional -= fractionBit
            decimalPlaces.append(1)
        } else {
            decimalPlaces.append(0)
        }
    }
    return decimalPlaces.toString()
}

private const val POINT = "."