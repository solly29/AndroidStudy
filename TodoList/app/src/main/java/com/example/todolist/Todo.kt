package com.example.todolist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//id가 기본키(PrimaryKey)가 된다.
open class Todo(@PrimaryKey var id: Long = 0, var title: String ="", var date: Long = 0) : RealmObject() {

}