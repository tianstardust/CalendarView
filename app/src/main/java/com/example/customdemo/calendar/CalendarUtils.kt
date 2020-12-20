package com.example.customdemo.calendar

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

object CalendarUtils {
    const val WEEK_START_SUN = 0
    const val WEEK_START_MON = 1

    fun getCurMonthCalendar(
        yearMonthEntity: YearMonthEntity,
        weekStartWith: Int
    ): ArrayList<CalendarEntity> {
        val date = Calendar.getInstance()
        date.set(yearMonthEntity.year, yearMonthEntity.month - 1, 1)
        val preDiff = getPreDiff(date, weekStartWith)
        val dayCount = date.getActualMaximum(Calendar.DAY_OF_MONTH)
        val nextDiff = getNextDiff(date, weekStartWith)
        val size = preDiff + dayCount + nextDiff
        val list = ArrayList<CalendarEntity>()
        for (i in 0 until size) {
            when (i) {
                in preDiff until preDiff + dayCount -> {
                    list.add(
                        CalendarEntity(
                            yearMonthEntity.year,
                            yearMonthEntity.month,
                            i - preDiff + 1, true
                        )
                    )
                }
                in 0 until preDiff -> {
                    date.set(yearMonthEntity.year, yearMonthEntity.month - 1, 1)
                    date.add(Calendar.DATE, (preDiff - i) * -1)
                    list.add(
                        CalendarEntity(
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH) + 1,
                            date.get(Calendar.DAY_OF_MONTH), false
                        )
                    )
                }
                in preDiff + dayCount until size -> {
                    date.set(yearMonthEntity.year, yearMonthEntity.month - 1, dayCount)
                    date.add(Calendar.DATE, i - preDiff - dayCount + 1)
                    list.add(
                        CalendarEntity(
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH) + 1,
                            date.get(Calendar.DAY_OF_MONTH), false
                        )
                    )
                }
            }
        }
        return list
    }

    //获取日历当月的总个数
    fun getCurMSize(yearMonthEntity: YearMonthEntity, weekStartWith: Int): Int {
        val date = Calendar.getInstance()
        date.set(yearMonthEntity.year, yearMonthEntity.month - 1, 1)
        val preDiff = getPreDiff(date, weekStartWith)
        val dayCount = date.get(Calendar.MONTH)
        val nextDiff = getNextDiff(date, weekStartWith)
        return preDiff + dayCount + nextDiff
    }


    //获取当月最初的偏移量
    fun getPreDiff(date: Calendar, weekStartWith: Int): Int {
        val week = date.get(Calendar.DAY_OF_WEEK)
        var preDiff = 0
        when (weekStartWith) {
            WEEK_START_SUN -> preDiff = week - 1
            WEEK_START_MON -> preDiff = if (week == 7) 6 else week
        }
        return preDiff
    }

    //获取当月最后的偏移量
    fun getNextDiff(date: Calendar, weekStartWith: Int): Int {
        date.add(Calendar.MONTH, 1)
        return 7 - getPreDiff(date, weekStartWith)
    }

    fun dipToPx(context: Context, dip: Int): Int {
        val density = context.resources.displayMetrics.density
        return (density * dip + 0.5).toInt()
    }

    fun spToPx(context: Context, sp: Int): Int {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return (scaledDensity * sp + 0.5f).toInt()
    }
}