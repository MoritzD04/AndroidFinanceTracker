package com.example.financetracker.repository

import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.model.FinanceList
import com.example.financetracker.util.FINANCE_ENTRY_2
import com.example.financetracker.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

interface IFinanceEntryRepository {
    // FinanceLists
    fun getFinanceLists(): StateFlow<List<FinanceList>>
    fun getFinanceLists(id: String): FinanceList?
    fun postFinanceList(list: FinanceList): FinanceList?
    fun patchFinanceList(list: FinanceList): FinanceList?
    fun deleteFinanceList(id: String)


    // Entries
    fun getEntries(listId: String): StateFlow<List<FinanceEntry>>
    fun getEntry(listId: String, id: String): FinanceEntry?
    fun postEntry(listId: String, entry: FinanceEntry): FinanceEntry?
    fun patchEntry(entry: FinanceEntry): FinanceEntry?
    fun deleteEntry(id: String)
}

@Singleton
class TestFinanceEntryRepository : IFinanceEntryRepository {
    private val logger = Logger(this.javaClass)

    private val _lists = MutableStateFlow<List<FinanceList>>(emptyList())
    override fun getFinanceLists(): StateFlow<List<FinanceList>> = _lists

    override fun getFinanceLists(id: String): FinanceList? = _lists.value.find { it.id == id }

    override fun postFinanceList(list: FinanceList): FinanceList? {
        _lists.update { current ->
            if(!current.any { it.id == list.id }){
                logger.info("Added List $list")
                current + list
            } else {
                logger.warn("List $list not added. Element already added.")
                current
            }
        }
        return list
    }

    override fun patchFinanceList(list: FinanceList): FinanceList? {
        var success = false
        _lists.update { current ->
            current.map {
                if (it.id == list.id) {
                    logger.info("Updated List $current to $list")
                    success = true
                    list
                } else {
                    it
                }
            }
        }
        if (!success) {
            logger.warn("Updating not Possible. No current value for $list")
            return null
        }else {
            return list
        }
    }

    override fun deleteFinanceList(id: String) {
        var deleted = false
        _lists.update { current ->
            current.filterNot {
                if (it.id == id) {
                    deleted = true
                    true
                } else false
            }
        }
        if (deleted) logger.info("Deleted List $id") else logger.warn("Deleting not possible. No list with id $id found.")
    }

    private val _entries = MutableStateFlow<List<FinanceEntry>>(emptyList())
    private val _entries2 = MutableStateFlow(listOf(FINANCE_ENTRY_2))

    override fun getEntries(listId: String): StateFlow<List<FinanceEntry>> = when(listId) {
        "0" -> _entries
        else -> _entries2
    }

    override fun getEntry(listId: String, id: String): FinanceEntry? =
        getEntries(listId).value.find { it.id == id }

    override fun postEntry(listId: String, entry: FinanceEntry): FinanceEntry? {
        _entries.update { current ->
            if(!current.any { it.id == entry.id }){
                logger.info("Added Entry $entry")
                current + entry
            } else {
                logger.warn("Entry $entry not added. Element already added.")
                current
            }
        }
        return entry
    }

    override fun patchEntry(entry: FinanceEntry): FinanceEntry? {
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
        if (!success) {
            logger.warn("Updating not Possible. No current value for $entry")
            return null
        }else {
            return entry
        }
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

