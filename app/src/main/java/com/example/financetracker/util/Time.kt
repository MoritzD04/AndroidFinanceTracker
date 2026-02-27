package com.example.financetracker.util

import java.time.Instant

fun Long.toLocalDate() = Instant.ofEpochMilli(this)
        .atZone(java.time.ZoneOffset.UTC)
        .toLocalDate()
