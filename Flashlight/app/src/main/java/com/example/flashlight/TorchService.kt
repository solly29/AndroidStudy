package com.example.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {
    // onCreate 에서 초기화를 해도 되지만 lazy를 이용해서 늦은 초기화가된다.
    private val torch: Torch by lazy { 
        Torch(this)
    }

    // 외부에서 startService()로 서비스를 호출하면 실행이된다.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){ // action에 on/ off가 들어있다.
            // 앱에서 실행할 경우
            "on" -> {
                torch.flashOn()
            }
            "off" -> {
                torch.flashOff()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
