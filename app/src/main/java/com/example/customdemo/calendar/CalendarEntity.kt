package com.example.customdemo.calendar

data class CalendarEntity(val year: Int, val month: Int, val day: Int, val isCurMonthDay: Boolean) {
    override fun toString(): String {
        return "$year" + (if (month < 10) "0$month" else "$month") + (if (day < 10) "0$day" else "$day")
    }
}