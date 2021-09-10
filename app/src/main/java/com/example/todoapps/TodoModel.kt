package com.example.todoapps

import android.content.ClipDescription
import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="TodoModel")
class TodoModel(
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="description") var description: String,
    @ColumnInfo(name="category") var category: String,
    @ColumnInfo(name="date") var date:Long,
    @ColumnInfo(name="time") var time:Long,
    @ColumnInfo(name="isFinished") var isFinished:Int=-1,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id")
    var id:Long=0L
)


//class Sample{
//    val user=TodoModel()
//}