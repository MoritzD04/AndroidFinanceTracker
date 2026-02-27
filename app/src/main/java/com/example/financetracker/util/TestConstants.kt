package com.example.financetracker.util

import com.example.financetracker.model.FinanceEntry
import java.time.LocalDate
import kotlin.random.Random

val FINANCE_ENTRY_1 = FinanceEntry(
    id = "0",
    name = "Rewe",
    value = 3004,
    date = LocalDate.of(2026, 1, 1)
)

val FINANCE_ENTRY_2 = FinanceEntry(
    id = "1",
    name = "Edeka",
    value = 2101,
    date = LocalDate.of(2026, 2, 5)
)

fun generateExampleFinanceEntries(count: Int): List<FinanceEntry> {
    val result = mutableListOf<FinanceEntry>()
    for (i in 1..count) {
        result.add(
            FinanceEntry(
                name = "TestEntry $i",
                value = Random.nextInt(50000)
            )
        )
    }
    return result
}