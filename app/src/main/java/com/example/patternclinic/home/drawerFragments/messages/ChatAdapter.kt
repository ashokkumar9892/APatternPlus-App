package com.example.patternclinic.home.drawerFragments.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.databinding.ItemMessageSenderBinding

class ChatAdapter(var list: MutableList<String>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    class ViewHolder(bind: ItemMessageSenderBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var bind =
            ItemMessageSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding) {
            tvMessage.text = list[position]
        }
    }


}