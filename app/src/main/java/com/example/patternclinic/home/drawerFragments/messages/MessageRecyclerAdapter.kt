package com.example.patternclinic.home.drawerFragments.messages

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.R

class MessageRecyclerAdapter(var context: Context) :
    RecyclerView.Adapter<MessageRecyclerAdapter.Viewfinder>() {
    inner class Viewfinder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ChatActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewfinder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_messages, parent, false)

        return Viewfinder(v)


    }

    override fun onBindViewHolder(holder: Viewfinder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}