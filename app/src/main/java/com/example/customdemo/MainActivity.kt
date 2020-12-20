package com.example.customdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import com.example.customdemo.calendar.CalendarUtils
import com.example.customdemo.calendar.YearMonthEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendar_view.initYearMonth(YearMonthEntity(2020,12),CalendarUtils.WEEK_START_SUN)
    }
}
