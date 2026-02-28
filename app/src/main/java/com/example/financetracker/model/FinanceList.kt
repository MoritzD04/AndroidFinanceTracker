package com.example.financetracker.model

import com.example.financetracker.R
import java.time.LocalDate
import java.util.UUID

data class FinanceList(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var date: LocalDate = LocalDate.now(),
    var image: Int = R.drawable.ic_launcher_foreground
)
