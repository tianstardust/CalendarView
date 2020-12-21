package com.example.calendar.calendar

/**
 *
 * Created by XuZhen on 2020/12/21 09:40
 */
object RangeSelectedEntity {
    var rangeSelectedStart: CalendarEntity? = null
    var rangeSelectedEnd: CalendarEntity? = null

    const val Out_Range = 0
    const val Only_Start = 1
    const val Not_Only_Start = 2
    const val In_Range = 3
    const val In_END = 4

    fun isSelected(calendarEntity: CalendarEntity): Boolean {
        if (rangeSelectedStart == null) return false
        if (rangeSelectedEnd == null) return rangeSelectedStart.toString() == calendarEntity.toString()
        if (calendarEntity.toString() >= rangeSelectedStart.toString() && calendarEntity.toString() <= rangeSelectedEnd.toString()) return true
        return false
    }

    /**
     * 只有在rangeSelectedStart 不为空的情况下，才能调用
     */
    fun getSelectedMode(calendarEntity: CalendarEntity): Int {
        if (rangeSelectedStart == null) return Out_Range
        if (calendarEntity.toString() == rangeSelectedStart.toString()) {
            return if (rangeSelectedEnd == null) {
                Only_Start
            } else {
                Not_Only_Start
            }
        }
        if (calendarEntity.toString() > rangeSelectedStart.toString()) {
            if (rangeSelectedEnd == null || calendarEntity.toString() > rangeSelectedEnd.toString()) return Out_Range
            if (calendarEntity.toString() < rangeSelectedEnd.toString()) return In_Range
            if (calendarEntity.toString() == rangeSelectedEnd.toString()) return In_END
        }
        return Out_Range
    }

    fun setSelectedCalendar(calendarEntity: CalendarEntity?) {
        if (calendarEntity == null) return
        if (rangeSelectedStart == null || (rangeSelectedStart != null && rangeSelectedEnd != null)) {
            rangeSelectedStart = calendarEntity
            rangeSelectedEnd = null
            refresh(
                YearMonthEntity(rangeSelectedStart!!.year, rangeSelectedStart!!.month), null
            )
//            getRange(rangeSelectedStart, rangeSelectedEnd)
        }
        if (rangeSelectedStart != null && rangeSelectedEnd == null) {
            if (calendarEntity.toString() <= rangeSelectedStart.toString()) {
                rangeSelectedStart = calendarEntity
                rangeSelectedEnd = null
                refresh(
                    YearMonthEntity(rangeSelectedStart!!.year, rangeSelectedStart!!.month), null
                )
//                getRange(rangeSelectedStart, rangeSelectedEnd)
            } else {
                rangeSelectedEnd = calendarEntity
                refresh(
                    YearMonthEntity(rangeSelectedStart!!.year, rangeSelectedStart!!.month),
                    YearMonthEntity(
                        rangeSelectedEnd!!.year, rangeSelectedEnd!!.month
                    )
                )
//                getRange(rangeSelectedStart, rangeSelectedEnd)
            }
        }
    }

    fun clearSelectedCalendar() {
        rangeSelectedStart = null
        rangeSelectedEnd = null
        refresh(rangeSelectedStart, rangeSelectedEnd)

    }

    lateinit var refresh: (start: YearMonthEntity?, end: YearMonthEntity?) -> Unit
    lateinit var getRange: (start: CalendarEntity?, end: CalendarEntity?) -> Unit
}