package com.example.financetracker.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.financetracker.util.FINANCE_ENTRY_1
import com.example.financetracker.util.FINANCE_ENTRY_2
import java.util.UUID

data class FinanceEntry(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var description: String = "",
    var value: Float = 0.0f,
    var image: Int
)

class FinanceEntryPreviewParameterProvider : PreviewParameterProvider<FinanceEntry> {
    override val values = sequenceOf(
        FINANCE_ENTRY_1,
        FINANCE_ENTRY_2
    )
}