package com.example.calendar.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.calendar.R

class CalendarView : View, View.OnClickListener {
    private val mWeekTitle = Paint()
    private val mCurMonthTextPaint = Paint()

    //文字默认高度16dp
    private var mTextSize = CalendarUtils.spToPx(context, 16)

    //每个Item 默认高度为56
    private var mItemHeight = CalendarUtils.dipToPx(context, 56)
    private var mBaseLineHeight = 0f
    private var mItemWidth = 0
    private var mLineCount = 0

    private val weekArray = context.resources.getStringArray(R.array.week_start_sun)
    private var calendarList = ArrayList<CalendarEntity>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
        obtainAttrs()
        updateParameter()
        setOnClickListener(this)
    }

    private fun updateParameter() {
        val fontMetrics = mCurMonthTextPaint.fontMetrics
        mBaseLineHeight =
            (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.descent + mItemHeight / 2
    }

    /**
     * 获取控件属性
     */
    private fun obtainAttrs() {

    }

    fun initYearMonth(yearMonthEntity: YearMonthEntity, weekStartWith: Int) {
        //获取当月的集合
        calendarList = CalendarUtils.getCurMonthCalendar(yearMonthEntity, weekStartWith)
        mLineCount = calendarList.size / 7
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mLineCount != 0) {
            val height =
                MeasureSpec.makeMeasureSpec((mLineCount + 1) * mItemHeight, MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mItemWidth = (width - paddingLeft - paddingRight) / 7
    }

    override fun onDraw(canvas: Canvas?) {
        var position = 0
        for (i in 0..mLineCount) {  //行数 height
            for (j in 0 until 7) {  //列数 width
                if (i == 0) {
                    drawWeekText(canvas, j)
                    continue
                }
                val calendarEntity = calendarList[position]
                if (!calendarEntity.isCurMonthDay) {
                    position++
                    continue
                }
                drawCurMonthText(canvas, calendarEntity, i, j)
                position++
            }
        }
    }

    private fun drawCurMonthText(canvas: Canvas?, calendarEntity: CalendarEntity, i: Int, j: Int) {
        val x = mItemWidth * j + paddingLeft
        val cx = mItemWidth / 2 + x
        val y = mItemHeight * i
        val baseLineY = y + mBaseLineHeight
        canvas?.drawText(
            calendarEntity.day.toString(), cx.toFloat(),
            baseLineY, mCurMonthTextPaint
        )
    }

    private fun drawWeekText(canvas: Canvas?, index: Int) {
        canvas?.drawText(
            weekArray[index],
            (mItemWidth / 2 + index * mItemWidth + paddingLeft).toFloat(),
            mBaseLineHeight,
            mWeekTitle
        )
    }

    private fun initPaint() {
        mWeekTitle.isAntiAlias = true
        mWeekTitle.isFakeBoldText = true
        mWeekTitle.color = Color.parseColor("#9E9E9E")
        mWeekTitle.textAlign = Paint.Align.CENTER
        mWeekTitle.textSize = mTextSize.toFloat()

        mCurMonthTextPaint.isAntiAlias = true
        mCurMonthTextPaint.isFakeBoldText = true
        mCurMonthTextPaint.color = Color.parseColor("#000000")
        mCurMonthTextPaint.textAlign = Paint.Align.CENTER
        mCurMonthTextPaint.textSize = mTextSize.toFloat()
    }

    var mX: Float = 0f
    var mY: Float = 0f
    var isClick = true
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.pointerCount!! > 1) return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.x
                mY = event.y
                isClick = true
            }
            MotionEvent.ACTION_MOVE -> {
                var mDy: Float
                if (isClick) {
                    mDy = event.y - mY
                    isClick = Math.abs(mDy) < 50
                }
            }
            MotionEvent.ACTION_UP -> {
                mX = event.x
                mY = event.y
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onClick(view: View?) {
        if (!isClick) return
        val selectedIndex = getSelectedIndex()
        if (selectedIndex != null) {
            Toast.makeText(context, selectedIndex.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedIndex(): CalendarEntity? {
        if (mItemWidth == 0 || mItemHeight == 0) return null
        if (mX <= paddingLeft || mX >= width - paddingRight) return null
        var row = ((mX - paddingLeft) / mItemWidth).toInt()
        if (row >= 7) {
            row = 6
        }
        if (mY - mItemHeight <= 0) return null
        var kind = (mY / mItemHeight).toInt() - 1
        var position = kind * 7 + row
        if (position in 0 until calendarList.size)
            return calendarList[position]
        return null
    }


}