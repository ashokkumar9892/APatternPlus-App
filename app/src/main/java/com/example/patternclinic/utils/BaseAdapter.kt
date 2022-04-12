package com.example.patternclinic.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(val layout:Int):RecyclerView.Adapter<BaseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ViewHolder(view)
    }


    class ViewHolder(val view:View):RecyclerView.ViewHolder(view)
}