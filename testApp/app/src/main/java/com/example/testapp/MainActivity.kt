package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        resultButton.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val w = weightEditText.text.toString()
            val h = heightEditText.text.toString()
            intent.putExtra("weight",w)
            intent.putExtra("height",h)
            saveData(w.toInt(),h.toInt())
            startActivity(intent)
            //Anko 라이브러리
            //이렇게 해도된다. startActivity<ResultActivity>(
            // "weight" to weightEditText.text.toString(),
            // "height" to heightEditText.text.toString()
            // )

        }
    }

    private fun saveData(w:Int,h:Int ){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_HEIGHT",h)
            .putInt("KEY_WEIGHT",w)
            .apply()//저장
    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val w = pref.getInt("KEY_WEIGHT",0)//저장된 값이 없으면 0을 반환한다.
        val h = pref.getInt("KEY_HEIGHT",0)

        if(w != 0 && h != 0){
            weightEditText.setText(w.toString())
            heightEditText.setText(h.toString())
        }
    }
}
