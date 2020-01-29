package com.example.tiltsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {//오버라이딩 할때 자동으로 해주는 단축키는 alt + enter
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //센서값이 변경되면 호출된다.
        //values[0] - x , [1] - y , [2] - z (-10~0 , 0~10)
        event?.let{
            //tag는 필터링할때 사용한다. , msg는 출력할 메시지이다.
            Log.d("MainActivity","onSensorChanged: x : ${it.values[0]}, y : ${it.values[1]}, z : ${it.values[2]}")
            //Log.e는 에러 표시, w는 경고표시, i는 정보성 로그 표시, v는 모든 로그표시
        }
    }

    private val sensorManager by lazy { //lazy는 이 변수를 처음 사용하면 밑에 있는 값으로 초기화 한다.
        getSystemService(Context.SENSOR_SERVICE) as SensorManager //as는 왼쪽에 있는 객체를 오늘쪽의 객체로 형변환한다.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        //센서 값을 받고(SensorEventListener을 클래스가 구현해야됨), 속도계 센서를 사용하고, 화면 방향이 전환될때 센서값을 얻는다.
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)//센서 설정
    }

    override fun onPause() {//화면이 꺼지기 직전
        super.onPause()
        sensorManager.unregisterListener(this)//센서 해제
    }
}
