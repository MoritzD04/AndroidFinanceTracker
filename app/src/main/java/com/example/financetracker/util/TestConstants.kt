package com.example.financetracker.util

import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.model.FinanceList
import kotlin.random.Random


val FINANCE_LIST_1 = FinanceList(
    id = "0",
    name = "Moritz",
    createdAt = instantFromStringAtMidnight("01.01.2026")
)
val FINANCE_LIST_2 = FinanceList(
    id = "1",
    name = "Baum",
    createdAt = instantFromStringAtMidnight("01.01.2026")
)
val FINANCE_ENTRY_1 = FinanceEntry(
    id = "0",
    name = "Rewe",
    value = 3004,
    date = instantFromStringAtMidnight("01.01.2026")
)

val FINANCE_ENTRY_2 = FinanceEntry(
    id = "1",
    listId = "1",
    name = "Edeka",
    value = 2101,
    date = instantFromStringAtMidnight("05.02.2026")
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