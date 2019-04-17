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
        with(dateCalendar) {
            time = dateFormatter.parse(dateStr)
            set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
            set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND))
            return this
        }
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

fun Calendar.asStringDate(dateStr: String, timeStr: String): String {
    val defaultLocale = Locale.getDefault()
    val timeCalendar = this.clone() as Calendar
    timeCalendar.time = SimpleDateFormat("HH:mm:ss", defaultLocale).parse(timeStr)
    val dateCalendar = this.clone() as Calendar
    with(dateCalendar) {
        time = SimpleDateFormat("dd/MM/yy", defaultLocale).parse(dateStr)
        set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
        set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
        set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND))
        return SimpleDateFormat("EEEE, d MMM yyyy HH.mm", Locale.getDefault()).also {
            it.timeZone = TimeZone.getTimeZone("UTC")
        }.format(time)
    }
}