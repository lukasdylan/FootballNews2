package com.lukasdylan.core.utility

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun calendarFromString(dateStr: String, timeStr: String): Calendar {
        val defaultLocale = Locale.getDefault()
        val timeFormatter = SimpleDateFormat("HH:mm:ss", defaultLocale)
        val timeCalendar = Calendar.getInstance()
        timeCalendar.time = timeFormatter.parse(timeStr)
        val dateFormatter = SimpleDateFormat("dd/MM/yy", defaultLocale)
        val dateCalendar = timeCalendar.clone() as Calendar
        dateCalendar.time = dateFormatter.parse(dateStr)
        dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
        dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
        dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND))

        return dateCalendar
    }

    fun formatAsDate(date: Date): String {
        return SimpleDateFormat("EEEE, d MMM yyyy HH.mm", Locale.getDefault()).also {
            it.timeZone = TimeZone.getTimeZone("UTC")
        }.format(date)
    }
}

fun String?.asGoalScorerFormat(): String {
    if (this.isNullOrBlank()) {
        return " - "
    }
    return this.replace(""":""", " : ").replace(""";""", ", ").dropLast(2)
}

fun String?.asPlayerListFormat(position: String): String {
    if (this.isNullOrBlank()) {
        return " - "
    }
    return this.replace("""; """, " ($position)\n").trimEnd()
}