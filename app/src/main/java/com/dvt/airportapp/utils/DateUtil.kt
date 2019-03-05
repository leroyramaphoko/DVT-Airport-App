package com.dvt.airportapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val DATE_FORMAT = "hh:mm"

    @JvmStatic
    fun formatDate(date: Date) : String {
        return formatDate(date, DATE_FORMAT)
    }

    @JvmStatic
    fun formatDate(date: Date, format: String) : String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

}