package com.example.financetracker.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.financetracker.R
import java.util.UUID

data class FinanceEntry(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var description: String = "",
    var value: Float = 0.0f,
    var image: Int
)

class FinanceEntryPreviewParameterProvider: PreviewParameterProvider<FinanceEntry> {
    override val values = sequenceOf(FinanceEntry(
        id = "0",
        name = "Rewe",
        description = "Einkauf",
        value = 30.04f,
        image = R.drawable.ic_launcher_foreground
        ),
        FinanceEntry(
            id = "1",
            name = "Edeka",
            description = "Einkauf",
            value = 21.01f,
            image = R.drawable.ic_launcher_foreground
    ))
}