package com.example.todoapps

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class TaskActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var cal: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    var finalDate = 0L
    var finalTime = 0L
    private val label =
        mutableListOf<String>("Business", "Personal", "Insurance", "Shopping", "Banking")
    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        tiet3.setOnClickListener(this)
        tiet4.setOnClickListener(this)
        btn1.setOnClickListener(this)
        getList()
        iv1.setOnClickListener(this)
        var title = tiet1.text
        var description = tiet2.text
        //var category=acs1.text
        //db.todoDao().insertTask(TodoModel(title,description,"Business"))


    }

      fun saveTask() {
        val category = acs1.selectedItem.toString()
        val title = til1.editText?.text.toString()
        val description = til2.editText?.text.toString()
       // viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
       // val user=TodoModel(title,description,category,finalDate,finalTime,-1)
         GlobalScope.launch(Dispatchers.Main) {
              withContext(Dispatchers.IO) {
                 return@withContext db.todoDao().insertTask(
                     TodoModel(
                         title,
                         description,
                         category,
                         finalDate,
                         finalTime
                     )
                 )
             }
             finish()
         }
       // startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tiet3 -> {
                setListener()
            }
            R.id.tiet4 -> {
                setTime()
            }
            R.id.iv1 -> {
                addItem()
            }
            R.id.btn1 -> {
                saveTask()
            }


        }
    }

    private fun addItem() {
        Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
    }

    private fun getList() {
        val adapterSpinner =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, label)
        label.sort()
        acs1.adapter = adapterSpinner
    }

    private fun setTime() {

        timeSetListener =
            TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                updateTime()
            }
        val timePickerDialog = TimePickerDialog(
            this, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun updateTime() {
        val myFormat = "h:mm a "
        val sdf = SimpleDateFormat(myFormat)
        tiet4.setText(sdf.format(cal.time))
        finalTime = cal.time.time

    }

    private fun setListener() {
        cal = Calendar.getInstance()
        dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()
            }
        val datePickerDialog = DatePickerDialog(
            this, dateSetListener,
            cal.get(Calendar.YEAR),

            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH )
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }


    private fun updateDate() {
        val myFormat = "EEE ,dd MM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        tiet3.setText(sdf.format(cal.time))
        til4.visibility = View.VISIBLE
        finalDate = cal.time.time
    }
}