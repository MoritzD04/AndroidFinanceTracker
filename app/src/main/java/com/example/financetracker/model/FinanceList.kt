package com.example.financetracker.model

import com.example.financetracker.R
import java.time.Instant
import java.util.UUID

data class FinanceList(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var createdAt: Instant = Instant.now(),
    var image: Int = R.drawable.ic_launcher_foreground
)
