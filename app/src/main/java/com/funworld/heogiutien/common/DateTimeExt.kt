package com.funworld.heogiutien.common

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocalDateTime.getDateAsString(): String =
    String.format("%d-%02d-%02d", year, dayOfMonth, monthValue)

fun LocalDateTime.getTimeWithoutSeconds(): String =
    String.format("%02d:%02d", hour, minute)

fun LocalDateTime.getAsPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return format(formatter)
}

fun LocalDateTime.getDateTimeAsMiliSeconds(): Long =
//    atOffset(ZoneOffset.of(ZoneId.of("VST", ZoneId.SHORT_IDS).id)).toInstant().toEpochMilli()
    atOffset(ZoneOffset.ofHours(7)).toInstant().toEpochMilli()