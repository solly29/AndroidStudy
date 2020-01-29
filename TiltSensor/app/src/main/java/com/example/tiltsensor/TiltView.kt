package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()
    private var cX: Float = 0f
    private var cY: Float = 0f

    init{
        greenPaint.color = Color.GREEN //초록색 바탕

        blackPaint.style = Paint.Style.STROKE //테두리만 그리도록
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cX,cY,100f,blackPaint)
        canvas?.drawCircle(cX,cY,100f,greenPaint)

        canvas?.drawLine(cX - 20,cY,cX + 20,cY,blackPaint)
        canvas?.drawLine(cX,cY - 20,cX,cY + 20,blackPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }
}