package com.example.financetracker.behavior.command

interface ICommand {
    fun execute()
    fun undo()
}