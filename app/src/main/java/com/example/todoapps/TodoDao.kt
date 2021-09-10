package com.example.todoapps

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTask(todoModel: TodoModel?)
    @Query("delete from TodoModel where id=:uid")
    suspend fun deleteTask(uid: Long)
    @Query("select * from TodoModel where isFinished=-1")
    fun getTask():LiveData<List<TodoModel>>
    @Query("update TodoModel set isFinished =1 where id=:uid")
    suspend fun finishedTask(uid: Long)
    @Query("select * from TodoModel where isFinished!=-1")
    fun doneTask():LiveData<List<TodoModel>>
}