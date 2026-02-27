package com.example.financetracker.util

import com.example.financetracker.model.FinanceEntry
import kotlin.random.Random
import kotlin.uuid.Uuid.Companion.random

val FINANCE_ENTRY_1 = FinanceEntry(
    name = "Rewe",
    description = "Einkauf",
    value = 3004
)

val FINANCE_ENTRY_2 = FinanceEntry(
id = "1",
name = "Edeka",
description = "Einkauf",
value = 2101
)

fun generateExampleFinanceEntries(count: Int) : List<FinanceEntry>{
    val result = mutableListOf<FinanceEntry>()
    for(i in 1..count) {
        result.add(FinanceEntry(
            name = "TestEntry $i",
            value = Random.nextInt(50000)
        ))
    }
    return result
}