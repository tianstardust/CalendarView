package com.example.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.calendar.calendar.CalendarViewAdapter
import com.example.calendar.calendar.YearMonthEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = ArrayList<YearMonthEntity>()
        for (i in 2019..2020) {
            for (j in 1..12) {
                data.add(YearMonthEntity(i, j))
            }
        }

        val context = this
        recycle.apply {
            adapter = CalendarViewAdapter(data)
            layoutManager = LinearLayoutManager(context)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }


}
