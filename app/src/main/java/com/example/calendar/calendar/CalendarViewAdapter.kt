package com.example.calendar.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import kotlinx.android.synthetic.main.item_calendar.view.*

class CalendarViewAdapter(var data: ArrayList<YearMonthEntity>) :
    RecyclerView.Adapter<CalendarViewAdapter.CalendarViewHolder>() {
    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val yearMonthEntity = data[position]
        holder.itemView.apply {
            item_calendar.initYearMonth(yearMonthEntity, CalendarUtils.WEEK_START_SUN)
            item_title.text = "" + yearMonthEntity.year + "-" + yearMonthEntity.month
        }
    }
}