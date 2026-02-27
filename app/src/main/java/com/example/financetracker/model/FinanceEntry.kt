package com.example.financetracker.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.financetracker.R
import com.example.financetracker.util.FINANCE_ENTRY_1
import com.example.financetracker.util.FINANCE_ENTRY_2
import java.time.LocalDate
import java.util.UUID

data class FinanceEntry(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var value: Int = 0,
    var date: LocalDate = LocalDate.now(),
    var image: Int = R.drawable.ic_launcher_foreground
)

class FinanceEntryPreviewParameterProvider : PreviewParameterProvider<FinanceEntry> {
    override val values = sequenceOf(
        FINANCE_ENTRY_1,
        FINANCE_ENTRY_2
    )
}