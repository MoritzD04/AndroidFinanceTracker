package com.example.financetracker.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financetracker.ui.composable.AddFinanceEntryMenu
import com.example.financetracker.ui.composable.BottomBar
import com.example.financetracker.ui.composable.FinanceEntryCard
import com.example.financetracker.ui.viewmodel.MainViewModel
import com.example.financetracker.util.convertToCents


@Composable
fun MainView(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val entries by viewModel.entries.collectAsState()
    var renderAddWindow by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                LazyColumn {
                    items(
                        items = entries,
                        key = { it.id }
                    ) { entry ->
                        FinanceEntryCard(
                            entry = entry,
                            onDeleteClick = { viewModel.deleteEntry(entry) }
                        )
                    }
                }
                Button(onClick = {renderAddWindow = true}) { Text("Add Entry") }
                if(renderAddWindow) {
                    AddFinanceEntryMenu {
                        viewModel.addEntry(it)
                        renderAddWindow = false
                    }
                }
            }
        }
    }
}