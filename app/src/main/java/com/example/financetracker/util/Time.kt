package com.example.financetracker.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Long.toLocalDate() = Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

val DD_MM_YYYY_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd.MM.uuuu")
                .withZone(ZoneOffset.UTC)

fun instantFromStringAtMidnight(date: String): Instant {
        return LocalDate.parse(date, DD_MM_YYYY_FORMATTER).atStartOfDay(ZoneOffset.UTC)
                .toInstant()
}