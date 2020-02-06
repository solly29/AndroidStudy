package com.example.todolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
//여기선 할일 목록을 보여주는 액티비티이다.
class MainActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // 테이블의 데이터 전체를 가져오고 date를 기준으로 내림차순 정렬
        val realmResult = realm.where<Todo>().findAll().sort("date", Sort.DESCENDING)
        val adapter = TodoListAdapter(realmResult) // 할일 목록을 전달해서 어댑터를 만든다.
        listView.adapter = adapter // 리스트뷰에 어댑터 설정

        // 데이터가 변경될때마다 어댑터에 변경을 알려준다. 리스트를 다시 표시한다.
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        // 리스트의 아이템을 클릭했을때
        listView.setOnItemClickListener { parent, view, position, id ->
            //할일 수정
            //인텐트로 id값을 인자로 넘겨 액티비티를 변경한다. Anko라이브러리임
            startActivity<EditActivity>("id" to id)
        }

        // 추가 버튼을 눌렀을때
        fab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
