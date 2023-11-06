package com.gongracr.core.utility

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatDate(date: String): String {
        val instant = Instant.parse(date)
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        return dateFormatter.format(zonedDateTime)
    }
}