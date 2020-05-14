package com.funworld.heogiutien.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        const val DATE_TIME_FORMAT = "YYYY-dd-MM HH:mm"

        fun getDateAsString(time: Long, format: String): String {
            val create = Date(time)
            val formatter = SimpleDateFormat(format)
            return formatter.format(create)
        }
    }
}

// research threetenabp for using below Android 8 - sdk 26
//https://stackoverflow.com/questions/38922754/how-to-use-threetenabp-in-android-project