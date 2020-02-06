package com.example.todolist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//id가 기본키(PrimaryKey)가 된다. 데이터베이스 테이블임
open class Todo(@PrimaryKey var id: Long = 0, var title: String ="", var date: Long = 0) : RealmObject() {

}