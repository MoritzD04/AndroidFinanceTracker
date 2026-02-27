package com.example.financetracker.behavior.command

import com.example.financetracker.model.FinanceEntry
import com.example.financetracker.repository.IFinanceEntryRepository

class AddFinanceEntryCommand(val repo: IFinanceEntryRepository, val entry: FinanceEntry): ICommand {
    override fun execute() {
        repo.postEntry(entry)
    }

    override fun undo() {
        repo.deleteEntry(entry.id)
    }
}

class RemoveFinanceEntryCommand(val repo: IFinanceEntryRepository, val entry: FinanceEntry): ICommand {
    var oldValue: FinanceEntry? = null
    override fun execute() {
        oldValue = entry.copy()
        repo.deleteEntry(entry.id)
    }

    override fun undo() {
        repo.postEntry(oldValue!!)
    }
}

class EditFinanceEntryCommand(val repo: IFinanceEntryRepository, val entry: FinanceEntry): ICommand {
    var oldValue: FinanceEntry? = null
    override fun execute() {
        oldValue = repo.getEntry(entry.id)
        repo.patchEntry(entry)
    }

    override fun undo() {
        repo.patchEntry(oldValue!!)
    }
}