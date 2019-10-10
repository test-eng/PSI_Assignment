package com.psi.commons

import java.text.SimpleDateFormat
import java.util.*

interface DateTimeUtil {
    fun provideCurrentTime(): String
    fun provideCurrentUnixTime(): Long
    fun toUnixTime(dateTime: String): Long
}

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

class DateTimeUtilImpl: DateTimeUtil {
    private val dateFormatter = SimpleDateFormat(DATE_FORMAT)

    override fun provideCurrentTime(): String {
        return dateFormatter.format(Date(System.currentTimeMillis()))
    }

    override fun provideCurrentUnixTime(): Long {
        return System.currentTimeMillis()
    }

    override fun toUnixTime(dateTime: String): Long {
        return dateFormatter.parse(dateTime)?.time ?: 0L
    }
}