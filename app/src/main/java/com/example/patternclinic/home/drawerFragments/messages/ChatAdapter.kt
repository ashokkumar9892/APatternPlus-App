package com.example.patternclinic.home.drawerFragments.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.databinding.ItemDocxMessageBinding
import com.example.patternclinic.databinding.ItemMessageReciveBinding
import com.example.patternclinic.databinding.ItemMessageSenderBinding
import com.example.patternclinic.databinding.ItemVideoMessageBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs

class ChatAdapter(var list: MutableList<Chatlist>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var SEND_TEXT = 1
    private var RECIEVE_TEXT = 2
    private var SEND_IMAGE_VIDEO = 3
    private var SEND_DOCX = 4

    //    private var  = 2
//    private var RECIEVE = 2
//    private var RECIEVE = 2
//    private var RECIEVE = 2
    val user_id = SharedPrefs.getLoggedInUser()?.patientInfo?.sk

    class SendHolder(bind: ItemMessageSenderBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    class RecieveHolder(bind: ItemMessageReciveBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    class VideoHolder(bind: ItemVideoMessageBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    class DocxHolder(bind: ItemDocxMessageBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    fun addMessage(message: Chatlist) {
        list.add(message)
        notifyItemInserted(list.size - 1)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            SEND_TEXT -> {
                val bind =
                    ItemMessageSenderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SendHolder(bind)
            }
            SEND_IMAGE_VIDEO -> {
                val bind =
                    ItemVideoMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return VideoHolder(bind)
            }

            SEND_DOCX -> {
                val bind =
                    ItemDocxMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return DocxHolder(bind)
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
            SEND_TEXT -> {
                val holder1 = holder as SendHolder
                with(holder1.binding) {
                    tvMessage.text = list[position].message
                }

            }

            SEND_IMAGE_VIDEO -> {
                val holder1 = holder as VideoHolder
                with(holder1.binding) {
                    Glide.with(holder1.itemView.context).load(list[position].message)
                        .into(ivImageMessage)
                }
            }
            RECIEVE_TEXT -> {

                val holder1 = holder as RecieveHolder
                with(holder1.binding) {
                    tvMessage.text = list[position].message
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].senderId == user_id) {

            if (list[position].chatType == Keys.FILE_TYPE_VIDEO || list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                return SEND_IMAGE_VIDEO
            } else if (list[position].chatType == Keys.FILE_TYPE_FILE) {
                return SEND_DOCX
            }

            return SEND_TEXT
        } else {
            return RECIEVE_TEXT
        }
    }

}