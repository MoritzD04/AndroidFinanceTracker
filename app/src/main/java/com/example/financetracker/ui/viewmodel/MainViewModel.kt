package com.example.financetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.R
import com.example.financetracker.behavior.command.AddFinanceEntryCommand
import com.example.financetracker.behavior.command.Invoker
import com.example.financetracker.behavior.command.RemoveFinanceEntryCommand
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.repository.IFinanceEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val financeEntryRepo: IFinanceEntryRepository
) : ViewModel() {
    val invoker = Invoker()

    val entries: StateFlow<List<FinanceEntry>> =
        financeEntryRepo.getEntries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    init {
        val cmd1 = AddFinanceEntryCommand(financeEntryRepo, FinanceEntry(
            name = "Rewe",
            description = "Einkauf",
            value = 30.04f,
            image = R.drawable.ic_launcher_foreground
        ))
        invoker.execute(cmd1)
    }

    fun deleteEntry(entry: FinanceEntry) = invoker.execute(RemoveFinanceEntryCommand(financeEntryRepo, entry))
}