package com.example.notes.util

import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    private const val DEFAULT_DATE_PATTERN = "dd/MM/yyyy HH:mm"

    fun formatDate(date: Date): String {
        val pattern = DEFAULT_DATE_PATTERN
        val df = SimpleDateFormat(pattern)
        return df.format(date)
    }
}