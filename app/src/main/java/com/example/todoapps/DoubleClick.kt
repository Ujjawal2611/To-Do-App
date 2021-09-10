package com.example.todoapps


import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView


class DoubleClick(context: Context, private val recyclerView: RecyclerView, private var mListener:OnItemClickListener?) :RecyclerView.OnItemTouchListener{


    private val mGestureDetector:GestureDetector

    init {
        mGestureDetector= GestureDetector(context,object :GestureDetector.SimpleOnGestureListener(){


            override fun onDoubleTap(e: MotionEvent?): Boolean {
                return true
            }
        })
    }
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView=rv.findChildViewUnder(e.x,e.y)
        if(childView!=null&&mListener!=null&&mGestureDetector.onTouchEvent(e)){
            mListener?.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    interface OnItemClickListener{
        fun onItemClick(view: View,position: Int)
        fun onItemLongClick(view: View,position: Int)
    }
}