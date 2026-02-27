package com.example.financetracker.behavior.command

class Invoker {
    private val history = mutableListOf<ICommand>()
    private var pointer = -1

    fun execute(cmd: ICommand) {
        // Clear redo history if we execute a new command
        if (pointer < history.size - 1) {
            history.subList(pointer + 1, history.size).clear()
            }
        history.add(cmd)
        pointer++
        cmd.execute()
    }
    fun undo() { if (pointer >= 0) history[pointer--].undo() }
    fun redo() { if (pointer < history.size - 1) history[++pointer].execute() }
}