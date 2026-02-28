package com.example.financetracker.repository

import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.util.Logger
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
    private val logger = Logger(this.javaClass)
    private val _entries = MutableStateFlow<List<FinanceEntry>>(emptyList())

    override fun getEntries(): StateFlow<List<FinanceEntry>> = _entries

    override fun getEntry(id: String): FinanceEntry? =
        _entries.value.find { it.id == id }

    override fun postEntry(entry: FinanceEntry) {
        _entries.update { current ->
            if(!current.any { it.id == entry.id }){
                logger.info("Added Entry $entry")
                current + entry
            } else {
                logger.warn("Entry $entry not added. Element already added.")
                current
            }
        }

    }

    override fun patchEntry(entry: FinanceEntry) {
        var success = false
        _entries.update { current ->
            current.map {
                if (it.id == entry.id) {
                    logger.info("Updated Entry $current to $entry")
                    success = true
                    entry
                } else {
                    it
                }
            }
        }
        if (!success) logger.warn("Updating not Possible. No current value for $entry")
    }

    override fun deleteEntry(id: String) {
        var deleted = false
        _entries.update { current ->
            current.filterNot {
                if (it.id == id) {
                    deleted = true
                    true
                } else false
            }
        }
        if (deleted) logger.info("Deleted Entry $id") else logger.warn("Deleting not possible. No entry with id $id found.")
    }
}

