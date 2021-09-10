package com.example.todoapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(var list:ArrayList<TodoModel>) :RecyclerView.Adapter<CustomAdapter.CustomViewHolder>(){
    class CustomViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: TodoModel){
            with(itemView){
                txtShowTask.text=todoModel.description
                txtTitle.text=todoModel.title
                txtCategory.text=todoModel.category
                updateTime(todoModel.time)
                updateDate(todoModel.date)
            }
        }

        private fun updateTime(time: Long) {
            val myformat = "h:mm a"
            val sdf = SimpleDateFormat(myformat)
            itemView.txtShowTime.text = sdf.format(Date(time))
        }

        private fun updateDate(date: Long) {
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            itemView.txtShowDate.text = sdf.format(Date(date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //TODO("Not yet implemented")
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.recycler_view,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
       val currentItem=list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }
 
    
}