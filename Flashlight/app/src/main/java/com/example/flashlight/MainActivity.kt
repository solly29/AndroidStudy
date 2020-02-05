package com.example.flashlight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val torch = Torch(this)

        // 엑티비티에서 스위치 온 되었을때 이벤트
        flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                //torch.flashOn()
                startService(intentFor<TorchService>().setAction("on")) // Anko 라이브러리
                /*이렇게 써도된다.
                val intent = Intent(this, TorchService::class.java)
                intent.action = "on"
                startService(intent)*/
            }else{
                //torch.flashOff()
                startService(intentFor<TorchService>().setAction("off"))
            }
        }
    }
}
