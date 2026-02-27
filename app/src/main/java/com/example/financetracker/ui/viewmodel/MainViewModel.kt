package com.example.financetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.behavior.command.AddFinanceEntryCommand
import com.example.financetracker.behavior.command.Invoker
import com.example.financetracker.behavior.command.RemoveFinanceEntryCommand
import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.repository.IFinanceEntryRepository
import com.example.financetracker.util.FINANCE_ENTRY_1
import com.example.financetracker.util.generateExampleFinanceEntries
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
        val cmd1 = AddFinanceEntryCommand(financeEntryRepo, FINANCE_ENTRY_1)
        invoker.execute(cmd1)
        generateExampleFinanceEntries(20).forEach {
            invoker.execute(AddFinanceEntryCommand(financeEntryRepo, it))
        }
    }

    // wrap in viewmodelscope if repo is async
    fun deleteEntry(entry: FinanceEntry) = invoker.execute(RemoveFinanceEntryCommand(financeEntryRepo, entry))

    fun addEntry(entry: FinanceEntry) = invoker.execute(AddFinanceEntryCommand(financeEntryRepo, entry))
}