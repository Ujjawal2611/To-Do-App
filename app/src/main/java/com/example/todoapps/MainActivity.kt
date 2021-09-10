package com.example.todoapps


import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity(),DoubleClick.OnItemClickListener {
    val list = arrayListOf<TodoModel>()
    var adapter = CustomAdapter(list)
    val db by lazy{
        AppDatabase.getDatabase(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView1.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter

        }
        recyclerView1.addOnItemTouchListener(DoubleClick(this,recyclerView1,this))
        updateRecyclerView()
        initswipe()


    }

    private fun initswipe() {
        val simpleItemTouchHelper= object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.bindingAdapterPosition
                if(direction==ItemTouchHelper.LEFT){
                    Log.d("tags","${list[position].id}")
                    GlobalScope.launch(Dispatchers.Main){
                        withContext(Dispatchers.IO){
                                db.todoDao().finishedTask(list[position].id)
                        }

                    }
                    updateRecyclerView()
                }
                else if(direction==ItemTouchHelper.RIGHT){
                    deleteTask(position)
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1->{
                startActivity(Intent(this,HistoryActivity::class.java))
                return true
            }
            R.id.menu2->{

                return true
            }
        }
        return true
    }
    private fun updateRecyclerView() {
        runOnUiThread{
            db.todoDao().getTask().observe(this, Observer {
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



    fun openNewTask(view: View) {
       view.setOnClickListener {

            startActivity(Intent(this,TaskActivity::class.java))
        }
    }
    fun deleteTask(position: Int){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                db.todoDao().deleteTask(list[position].id)
                runOnUiThread{
                    adapter.notifyDataSetChanged()
                }
            }

        }
        updateRecyclerView()
    }
    override fun onItemClick(view: View, position: Int) {


    }

    override fun onItemLongClick(view: View, position: Int) {
        val alert=AlertDialog.Builder(this)
        alert.apply {
            setCancelable(true)
            setTitle("Delete The Task")
            setIcon(R.drawable.todolist)
            setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                deleteTask(position)
            })
            setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->  })
        }
        alert.show()
    }
}