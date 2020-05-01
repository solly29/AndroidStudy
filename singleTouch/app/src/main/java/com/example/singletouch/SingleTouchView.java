package com.example.singletouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SingleTouchView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();

    public SingleTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       paint.setAntiAlias(true);
       paint.setStrokeWidth(10f); // 굵기
       paint.setColor(Color.BLUE);
       paint.setStyle(Paint.Style.STROKE);
       paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }
}
