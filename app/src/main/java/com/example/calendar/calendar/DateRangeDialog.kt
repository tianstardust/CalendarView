package com.example.calendar.calendar

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.calendar.R
import kotlinx.android.synthetic.main.dialog_date_range.*
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * Created by XuZhen on 2020/12/21 13:25
 */
class DateRangeDialog : Dialog {
    private var range = 2
    private val data = ArrayList<YearMonthEntity>()
    private val curDate = Calendar.getInstance()
    private val yearMonthEntity =
        YearMonthEntity(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH) + 1)

    constructor(context: Context) : this(context, R.style.RangeDialog)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_date_range)
        initData()
        initView()
        setShowMode()
    }

    private fun setShowMode() {
        window?.decorView?.setPadding(0, 0, 0, 0)
        val attributes = window?.attributes
        attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = attributes
        window?.setGravity(Gravity.BOTTOM)
    }

    private fun initData() {
        for (i in 0 until range) {
            for (j in 1..12) {
                data.add(YearMonthEntity(yearMonthEntity.year - range + 1 + i, j))
            }
        }
    }

    private fun initView() {
        RangeSelectedEntity.getRange = { start, end ->
            dialog_tv_range_start_content.apply {
                text = start?.toString() ?: ""
            }
            dialog_tv_range_end_content.apply {
                text = end?.toString() ?: ""
            }
        }
        dialog_range_recycle.apply {
            adapter = CalendarViewAdapter(data)
            layoutManager = LinearLayoutManager(context)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            val index = data.indexOf(yearMonthEntity)
            scrollToPosition(index)
        }
        dialog_tv_reset.setOnClickListener {
            RangeSelectedEntity.clearSelectedCalendar()
            dialog_tv_range_start_content.text = ""
            dialog_tv_range_end_content.text = ""
        }
        dialog_tv_fix.setOnClickListener {
            if (TextUtils.isEmpty(dialog_tv_range_start_content.toString()) ||
                TextUtils.isEmpty(dialog_tv_range_end_content.toString())
            ) {
                Toast.makeText(context, "起始或截止时间为空，请重新选择", Toast.LENGTH_SHORT).show()
            } else {
                getRangeStartEnd(
                    dialog_tv_range_start_content.text.toString(),
                    dialog_tv_range_end_content.text.toString()
                )
                dismiss()
            }
        }
        dialog_range_image_close.setOnClickListener {
            RangeSelectedEntity.clearSelectedCalendar()
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        RangeSelectedEntity.clearSelectedCalendar()
    }

    lateinit var getRangeStartEnd: (startString: String, endString: String) -> Unit
}