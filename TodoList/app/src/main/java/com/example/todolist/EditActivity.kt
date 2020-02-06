package com.example.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*
// 할일을 추가하는 액티비티이다.
class EditActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() // realm 인스턴스 얻기
    val calendar: Calendar = Calendar.getInstance() // 오늘날짜로 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // 인텐트로 넘어온 id값이 없으면 -1L으로
        val id = intent.getLongExtra("id",-1L)
        if(id == -1L)
            insertMode()
        else
            updateMode(id)

        // 캘린더 뷰의 날짜를 선택했을 때 calendar 객체에 설정
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year) //년
            calendar.set(Calendar.MONTH, month) //월
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth) //일
        }
    }

    // 추가 모드
    @SuppressLint("RestrictedApi")
    private fun insertMode(){
        // 삭제버튼 감추기
        // visible는 보이게, invisible는 영역은 차지하는데 보이지 않는다. 밑에건 완전히 안보인다.
        deleteFab.visibility = View.GONE

        // 완료 버튼을 클릭하면 추가
        doneFab.setOnClickListener {
            insertTodo()
        }
    }

    // 수정모드
    private fun updateMode(id: Long){
        val todo = realm.where<Todo>().equalTo("id",id).findFirst()!!
        // 해당 id에 대한 데이터를 화면에 표시
        todoEditText.setText(todo.title)
        calendarView.date = todo.date

        doneFab.setOnClickListener {
            updateTodo(id)
        }

        // 삭제 버튼 클릭
        deleteFab.setOnClickListener {
            deleteTodo(id)
        }
    }

    override fun onDestroy() { // 액티비티가 소멸되면 호출된다.
        super.onDestroy()
        realm.close() // 인스턴스 해제
    }

    private fun insertTodo(){
        realm.beginTransaction() // 트랜잭션 시작, 트랜잭션은 데이터베이스의 작업 단위이다. 이 밑의 코드들이 오류나면 작업이 취소가된다.

        val newItme = realm.createObject<Todo>(nextId()) // 새 객체 생성, 괄호안은 기본키 값을 넣는다. <>안에는 테이블 이름이다.
        newItme.title = todoEditText.text.toString() // 할일 이름
        newItme.date = calendar.timeInMillis // 날짜

        realm.commitTransaction() // 트랜잭션 종료 반영(저장)

        // 추가 되면 메시지 출력
        alert("내용이 추가되었습니다."){
            yesButton { finish() }//finish으로 현재 액티비티 종료
        }.show()
    }

    // 기본키 자동 증가 기능이 없어서 이렇게 만듬
    private fun nextId(): Int{
        // where으로 Todo테이블의 모든값을 가지고오고 mac()으로 필드 이름이 id인 값들중 가장 큰 값을 반환한다.
        val maxId = realm.where<Todo>().max("id")
        if(maxId != null){
            return maxId.toInt() + 1
        }
        return 0
    }

    private fun updateTodo(id: Long){
        realm.beginTransaction()

        // id 칼럼에 해당 id가 있으면 첫 번째 데이터를 가져온다. !!는 왜쓰지?
        val updateItem = realm.where<Todo>().equalTo("id", id).findFirst()!!

        updateItem.title = todoEditText.text.toString()
        updateItem.date = calendar.timeInMillis

        realm.commitTransaction()

        alert("내용이 변경되었습니다."){
            yesButton { finish() }
        }.show()
    }

    private fun deleteTodo(id: Long){
        realm.beginTransaction()
        val deleteItem = realm.where<Todo>().equalTo("id",id).findFirst()!!
        deleteItem!!.deleteFromRealm() // 삭제
        realm.commitTransaction()

        alert("내용이 삭제되었습니다."){
            yesButton { finish() }
        }.show()
    }
}
