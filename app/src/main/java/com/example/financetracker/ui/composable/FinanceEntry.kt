package com.example.financetracker.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.model.FinanceEntryPreviewParameterProvider
import com.example.financetracker.repository.IFinanceEntryRepository
import com.example.financetracker.ui.viewmodel.MainViewModel

@Preview
@Composable
fun FinanceEntryCard(
    @PreviewParameter(FinanceEntryPreviewParameterProvider::class) entry: FinanceEntry,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = entry.image),
            contentDescription = "Item Icon",
            modifier = Modifier
                .size(64.dp)
                .padding(2.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.Start) {
            Text(text = entry.name, fontSize = 18.sp)
            Text(text = entry.description, fontSize = 11.sp)
        }

        Text(text = "${entry.value}€", fontSize = 16.sp, textAlign = TextAlign.End)
        IconButton(onClick = onEditClick) {
            Icon(Icons.Filled.Edit, contentDescription = null)
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
        }
    }
}