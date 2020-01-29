package com.example.stopwatch

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            isRunning = !isRunning

            if(isRunning)
                start()
            else
                pause()
        }

        labButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener{
            reset()
        }
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {//여기서 ui조작 가능
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun recordLapTime(){
        val t = this.time;
        val testView = TextView(this)
        testView.text = "$lap LAB : ${t / 100}.${t % 100}"
        labLayout.addView(testView)
        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        lap++
    }

    private fun reset(){
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        labLayout.removeAllViews()
        lap = 1
    }
}
