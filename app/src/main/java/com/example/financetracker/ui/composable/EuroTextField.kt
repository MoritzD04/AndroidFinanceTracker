package com.example.financetracker.ui.composable

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EuroTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = { input ->
            val normalized = input.replace(",", ".")
            val validPattern = Regex("""^-?\d*\.?\d{0,2}$""")

            if (normalized.isEmpty() || validPattern.matches(normalized)) {
                val numericValue = normalized.toDoubleOrNull() ?: 0.0
                if (kotlin.math.abs(numericValue) < 1_000_000.0) {
                    onValueChange(normalized)
                }
            }
        },
        modifier = Modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                val numericValue = value.toDoubleOrNull() ?: 0.0
                if (numericValue == 0.0) {
                    onValueChange("")
                }
            } else {
                // Format String on Keyboard close
                if (value.isNotEmpty()) {
                    val numericValue = value.toDoubleOrNull() ?: 0.0
                    val formatted = "%.2f".format(java.util.Locale.US, numericValue)
                    onValueChange(formatted)
                } else {
                    onValueChange("0.00")
                }
            }
        },
        label = { Text("Betrag (€)") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Force clear focus
                focusManager.clearFocus()
            }
        ),
        singleLine = true
    )
}
