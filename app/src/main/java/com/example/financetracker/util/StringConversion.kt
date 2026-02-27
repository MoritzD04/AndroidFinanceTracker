package com.example.financetracker.util

import java.math.BigDecimal

fun convertToCents(value: String): Int {
    if (value.isBlank()) return 0
    return BigDecimal(value)
        .multiply(BigDecimal("100"))
        .toInt()
}

fun Int.asEuroString(): String {
    val ones = this % 10
    val tens = (this / 10) % 10
    return "${(this - 10*tens - ones)/100}.${tens}${ones}€"
}