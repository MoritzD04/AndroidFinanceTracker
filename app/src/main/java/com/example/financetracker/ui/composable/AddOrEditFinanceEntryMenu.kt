package com.example.financetracker.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.util.asEuroString
import com.example.financetracker.util.convertToCents
import java.time.LocalDate

@Composable
fun AddOrEditFinanceEntryMenu(editing: MutableState<FinanceEntry?>? = null, onEditFinish: (FinanceEntry) -> Unit = {}, onAdd: (FinanceEntry) -> Unit = {}) {
    var name by remember { mutableStateOf(editing?.value?.name ?: "") }
    val selectedDate = remember { mutableStateOf<LocalDate>(editing?.value?.date ?: LocalDate.now()) }
    var amountText by remember { mutableStateOf(editing?.value?.value?.asEuroString() ?: "0.00") }
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
            onAdd(FinanceEntry(
                name = name,
                date = selectedDate.value,
                value = convertToCents(amountText)
            ))
            onEditFinish(FinanceEntry(
                id = editing!!.value!!.id,
                name = name,
                date = selectedDate.value,
                value = convertToCents(amountText)
            ))
            // reset values
            name = ""
            selectedDate.value = LocalDate.now()
            amountText = "0.00"
        }) { Text("Add") }
    }

}

