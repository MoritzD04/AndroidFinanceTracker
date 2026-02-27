package com.example.financetracker.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.util.convertToCents
import java.time.LocalDate

@Composable
fun AddFinanceEntryMenu(onClick: (FinanceEntry) -> Unit) {
    var name by remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    var amountText by remember { mutableStateOf("0.00") }
    Column {
        Text("Add Entry")
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Type") },
            singleLine = true
        )
        DatePickerTextField(selectedDate)
        EuroTextField(value = amountText, onValueChange = { amountText = it })
        Button(onClick = {
            onClick(
                FinanceEntry(
                    name = name,
                    date = selectedDate.value,
                    value = convertToCents(amountText)
                )
            )
            // reset values
            name = ""
            selectedDate.value = LocalDate.now()
            amountText = "0.00"
        }) { Text("Add") }
    }

}

