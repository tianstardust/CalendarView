package com.example.calendar.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import kotlinx.android.synthetic.main.item_calendar.view.*


class CalendarViewAdapter(var data: ArrayList<YearMonthEntity>) :
    RecyclerView.Adapter<CalendarViewAdapter.CalendarViewHolder>() {

    init {
        RangeSelectedEntity.refresh = { start, end ->
            if (end == null) {
                notifyDataSetChanged()
            } else {
                val startIndex = data.indexOf(start)
                val endIndex = data.indexOf(end)
                notifyItemRangeChanged(startIndex, endIndex)
            }
        }
    }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val yearMonthEntity = data[position]
        holder.itemView.apply {
            item_calendar.initYearMonth(yearMonthEntity, CalendarUtils.WEEK_START_MON)
            item_title.text = "${yearMonthEntity.year}-${yearMonthEntity.month}"
        }
    }
}