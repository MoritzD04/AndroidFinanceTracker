package com.example.financetracker.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.ui.composable.AddOrEditFinanceEntryMenu
import com.example.financetracker.ui.composable.BottomBar
import com.example.financetracker.ui.composable.FinanceEntryCard
import com.example.financetracker.ui.composable.SimpleDialog
import com.example.financetracker.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.forEach


@Composable
fun MainView(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val lists by viewModel.lists.collectAsState()
    val openAddEntryDialog = remember { mutableStateOf(false) }
    val currentEditing = remember { mutableStateOf<FinanceEntry?>(null) }
    when {
        openAddEntryDialog.value -> SimpleDialog({ openAddEntryDialog.value = false }) {
            AddOrEditFinanceEntryMenu(onAdd = {
                viewModel.addEntry(it)
                openAddEntryDialog.value = false
            })
        }
        currentEditing.value != null -> {
            SimpleDialog({ openAddEntryDialog.value = false }) {
                AddOrEditFinanceEntryMenu(currentEditing, onEditFinish = {
                    currentEditing.value = null
                    viewModel.updateEntry(it)
                })
            }
        }

    }
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val state = rememberLazyListState()

            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = 80.dp // padding for button
                ),
                state = state
            ) {
                items(
                    items = lists,
                    key = { it.id }
                ) { list ->
                    Text(list.name)
                    Column {
                        viewModel.getEntries(list.id).collectAsState().value.forEach {  entry ->
                            FinanceEntryCard(
                                entry = entry,
                                onDeleteClick = { viewModel.deleteEntry(entry) },
                                onEditClick = { currentEditing.value = entry }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { openAddEntryDialog.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(64.dp)
                    .padding(4.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) { Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(48.dp)) }
        }
    }
}