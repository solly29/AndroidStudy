package com.example.todolist

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

//어댑터 클래스이다.
class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>) : RealmBaseAdapter<Todo>(realmResult) {
    // 리스트 뷰의 각 아이템에 표시할 뷰를 구성한다. 각 아이템은 이 메서드를 한번씩 호출한다.
    // 인자는 아이템 위치, 재활용되는 아이템 뷰, 부모 뷰(여기선 리스트 뷰를 참조한다.)
    // convertView는 view가 한번이라도 작성되면 작성했던 뷰를 전달한다.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View

        if(convertView == null){ // 처음 작성되는 뷰이면 null이다.
            // LayoutInflater으로 xml 파일을 코드로 불러오고, inflate으로 파일을 뷰로 반환해준다.
            // 인자는 불러올 xml 리소스 id, 불러올 파일이 붙을 뷰그룹, xml파일을 불러올때는 false
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todo,parent,false)
            vh = ViewHolder(view)
            view.tag = vh // tag에 뷰홀더를 저장한다. 여기는 Any형으로 어떠한 객체도 저장가능하다.
        }else{ // 이전에 작성한 뷰를 재사용한다.
            view = convertView // 전역 변수에 뷰를 넣고
            vh = view.tag as ViewHolder // tag 프로퍼티에 있는 뷰홀더를 꺼내서 ViewHolder로 형변환하고 저장
        }

        //adapterData에서 데이터를 접근할수 있다.
        if(adapterData != null){// 데이터가 있으면
            val item = adapterData!![position] // 해당 위치의 item을 저장(널 확인은 안한다(!!).)
            vh.textTextView.text = item.title
            vh.dateTextView.text = DateFormat.format("yyyy/MM/dd", item.date)
        }

        return view
    }

    // 리스트 뷰의 아이템의 id를 결정하는 메소드
    //여기선 데이터베이스의 레코드마다 고유한 아이디를 반환한다.
    override fun getItemId(position: Int): Long {
        if(adapterData != null){
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

// 뷰홀더 클래스 별도의 클래스이다.
class ViewHolder(view: View){
    //각 id의 참조를 저장한다.
    val dateTextView: TextView = view.findViewById(R.id.text1)
    val textTextView: TextView = view.findViewById(R.id.text2)
}