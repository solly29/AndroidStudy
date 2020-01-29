package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()
    private var cX: Float = 0f
    private var cY: Float = 0f
    private var xCoord: Float = 0f
    private var yCoord:Float = 0f

    init{
        greenPaint.color = Color.GREEN //초록색 바탕

        blackPaint.style = Paint.Style.STROKE //테두리만 그리도록
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cX,cY,100f,blackPaint)
        canvas?.drawCircle(xCoord + cX,yCoord + cY,100f,greenPaint)

        canvas?.drawLine(cX - 20,cY,cX + 20,cY,blackPaint)
        canvas?.drawLine(cX,cY - 20,cX,cY + 20,blackPaint)
    }

    //뷰의 크기가 결정되면 호출된다.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    //센서의 좌표값에 따라 원을 움직인다.
    fun onSensorEvent(event: SensorEvent){
        //화면을 가로로 돌렀어서 x축과 y축을 서로 바꿈
        yCoord = event.values[0] * 20 //그냥 좌표 그대로 하면 운직이는게 잘 안보인다 그래서 대충 20으로 함
        xCoord = event.values[1] * 20

        invalidate() //뷰의 onDraw() 메서드를 다시 호출한다. 다시 그린다.
    }
}