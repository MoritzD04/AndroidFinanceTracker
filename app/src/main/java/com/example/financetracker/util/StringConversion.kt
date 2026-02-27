package com.example.financetracker.util

import java.math.BigDecimal

fun convertToCents(value: String): Int {
    if (value.isBlank()) return 0
    return BigDecimal(value)
        .multiply(BigDecimal("100"))
        .toInt()
}