package com.example.financetracker.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import com.example.financetracker.util.DD_MM_YYYY_FORMATTER
import com.example.financetracker.util.instantFromStringAtMidnight
import java.time.Instant
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(selectedDate: MutableState<Instant>) {
    val showDatePicker = remember { mutableStateOf(false) }

    var text by remember(selectedDate.value) {
        mutableStateOf(DD_MM_YYYY_FORMATTER.format(selectedDate.value))
    }

    val isError = remember { mutableStateOf(false) }

    Row {
        TextField(
            value = text,
            onValueChange = { input ->
                // Force DD.MM.YYYY Pattern
                // Force 01-31 for Days, 01-12 for Months, 1900-9999 for year
                val pattern = Regex("^((?:0[1-9]|[12]\\d|3[01])(?:\\.(?:0[1-9]|1[012])(?:\\.(?:1(?:9(?:\\d{0,2})?)?|[2-9](?:\\d{0,3})?)?)?)?\$|^[0-3]\$|^(?:0[1-9]|[12]\\d|3[01])\\.\$|^(?:0[1-9]|[12]\\d|3[01])\\.[01])?\$")
                if (input.matches(pattern)) {
                    text = input

                    val parsed = try {
                        if (input.length == 10)
                            instantFromStringAtMidnight(input)
                        else null
                    } catch (_: Exception) {
                        null
                    }

                    isError.value = input.length == 10 && parsed == null
                    if (parsed != null) selectedDate.value = parsed
                }
            },
            label = { Text("Date") },
            placeholder = { Text("DD.MM.YYYY") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError.value,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        val finalParsed = try {
                            LocalDate.parse(text, DD_MM_YYYY_FORMATTER)
                        } catch (_: Exception) {
                            null
                        }

                        if (finalParsed == null) {
                            // reset to last viable date
                            text = DD_MM_YYYY_FORMATTER.format(selectedDate.value)
                            isError.value = false
                        }
                    } else {
                        text = ""
                    }
                },
        )

        IconButton(
            onClick = { showDatePicker.value = true },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        }
    }
    when {
        showDatePicker.value -> {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate.value.toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate.value = Instant.ofEpochMilli(it)
                            text = DD_MM_YYYY_FORMATTER.format(selectedDate.value)
                        }
                        showDatePicker.value = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker.value = false }) {
                        Text("Cancel")
                    }
                }
            ) { DatePicker(state = datePickerState, showModeToggle = false) }
        }
    }
}