package com.example.patternclinic.home.drawerFragments.messages

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.R
import com.example.patternclinic.data.model.ChatInfo
import com.example.patternclinic.data.model.GetUserChatResponse
//import com.example.patternclinic.data.model.Argument
import com.example.patternclinic.databinding.ItemMessagesBinding
import com.example.patternclinic.utils.Keys
import com.google.gson.Gson

class MessageRecyclerAdapter(var list: MutableList<ChatInfo>) :
    RecyclerView.Adapter<MessageRecyclerAdapter.Viewfinder>() {
    inner class Viewfinder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bind = ItemMessagesBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(itemView.context, ChatActivity::class.java).putExtra(
                        Keys.CHAT, Gson().toJson(list[absoluteAdapterPosition])
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewfinder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_messages, parent, false)

        return Viewfinder(v)


    }

    override fun onBindViewHolder(holder: Viewfinder, position: Int) {
        with(holder) {
            bind.tvName.text = list[position].name ?: ""
            bind.tvMessage.text = list[position].message ?: ""
            if (list[position].unseenCount == 0) {
                bind.tvCount.visibility = View.GONE
            } else {
                bind.tvCount.text = list[position].unseenCount?.toString()
            }
            if (list[position].chatStatus == Keys.STATUS_ACTIVE) {
                bind.llUserChatContainer.alpha=1F
            } else {
                bind.llUserChatContainer.alpha= 0.4F
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}