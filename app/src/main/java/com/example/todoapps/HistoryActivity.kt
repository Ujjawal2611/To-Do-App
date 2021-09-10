package com.example.todoapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class HistoryActivity : AppCompatActivity() {
    val db by lazy {
        AppDatabase.getDatabase(this)
    }
    private val list= arrayListOf<TodoModel>()
    private val adapter=CustomAdapter(list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        recyclerView2.apply {
            layoutManager=LinearLayoutManager(this@HistoryActivity)
            adapter=this@HistoryActivity.adapter

        }
        db.todoDao().doneTask().observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                list.clear()
                adapter.notifyDataSetChanged()
            }
        })


    }


}