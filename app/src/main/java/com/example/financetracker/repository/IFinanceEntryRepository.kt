package com.example.financetracker.repository

import android.util.Log
import com.example.financetracker.model.FinanceEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

interface IFinanceEntryRepository {
    fun getEntries(): StateFlow<List<FinanceEntry>>

    fun getEntry(id: String): FinanceEntry?

    fun postEntry(entry: FinanceEntry)

    fun patchEntry(
        entry: FinanceEntry
    )

    fun deleteEntry(id: String)
}
@Singleton
class TestFinanceEntryRepository : IFinanceEntryRepository {

    private val _entries = MutableStateFlow<List<FinanceEntry>>(emptyList())

    override fun getEntries(): StateFlow<List<FinanceEntry>> = _entries

    override fun getEntry(id: String): FinanceEntry? =
        _entries.value.find { it.id == id }

    override fun postEntry(entry: FinanceEntry) {
        _entries.update { current ->
            current + entry
        }
        Log.i("FinanceRepo", "Added Entry $entry")
    }

    override fun patchEntry(entry: FinanceEntry) {
        _entries.update { current ->
            current.map {
                if (it.id == entry.id) {
                    Log.i("FinanceRepo", "Updated Entry $current to $entry")
                    entry
                } else {
                    Log.i("FinanceRepo", "Updating not Possible. No current value for $entry")
                    it
                }
            }
        }
    }

    override fun deleteEntry(id: String) {
        _entries.update { current ->
            current.filterNot { it.id == id }
        }
        Log.i("FinanceRepo", "Deleted Entry $id")
    }
}

