package com.example.patternclinic.home.drawerFragments.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.databinding.ItemMessageReciveBinding
import com.example.patternclinic.databinding.ItemMessageSenderBinding

class ChatAdapter(var list: MutableList<Chatlist>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var SEND = 1
    var RECIEVE = 2

    class SendHolder(bind: ItemMessageSenderBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }
    class RecieveHolder(bind: ItemMessageReciveBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }
    fun addMessage(message:Chatlist){
        list.add(message)
        notifyItemInserted(list.size-1)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            SEND -> {
                val bind =
                    ItemMessageSenderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SendHolder(bind)
            }

        }

        val bind =
            ItemMessageReciveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecieveHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            SEND -> {
                val holder1 = holder as SendHolder
                        with(holder1.binding) {
                    tvMessage.text = list[position].message
                }

            }
            RECIEVE -> {

                val holder1 = holder as RecieveHolder
                with(holder1.binding) {
                    tvMessage.text = list[position].message
                }

            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].senderId == "PATIENT_1650619112058") {
            return SEND
        } else {
            return RECIEVE
        }
    }

}