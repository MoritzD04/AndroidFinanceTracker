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
import com.example.financetracker.util.toLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(selectedDate: MutableState<LocalDate>) {
    val showDatePicker = remember { mutableStateOf(false) }

    val formatter = remember {
        DateTimeFormatter.ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT)
    }

    var text by remember(selectedDate.value) {
        mutableStateOf(selectedDate.value.format(formatter))
    }

    var isError by remember { mutableStateOf(false) }

    Row {
        TextField(
            value = text,
            onValueChange = { input ->

                // Force DD.MM.YYYY Pattern
                val pattern = Regex("""^(([0-2]?\d|3[01])(\.(([01]?\d)(\.(\d{0,4}))?)?)?)?$""")
                if (input.isEmpty() || input.matches(pattern)) {
                    text = input

                    val parsed = try {
                        if (input.length == 10)
                            LocalDate.parse(input, formatter)
                        else null
                    } catch (_: Exception) {
                        null
                    }

                    isError = input.length == 10 && parsed == null
                    if (parsed != null) selectedDate.value = parsed
                }
            },
            label = { Text("Date") },
            placeholder = { Text("DD.MM.YYYY") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        val finalParsed = try {
                            LocalDate.parse(text, formatter)
                        } catch (_: Exception) {
                            null
                        }

                        if (finalParsed == null) {
                            // reset to last viable date
                            text = selectedDate.value.format(formatter)
                            isError = false
                        }
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
                initialSelectedDateMillis = selectedDate.value
                    .atStartOfDay(java.time.ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate.value = it.toLocalDate()
                            text = selectedDate.value.format(formatter)
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