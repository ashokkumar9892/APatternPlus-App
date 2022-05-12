package com.example.patternclinic.home.drawerFragments.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.databinding.*
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs

class ChatAdapter(var list: MutableList<Chatlist>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var SEND_TEXT = 1
    private var RECIEVE_TEXT = 2
    private var SEND_IMAGE_VIDEO = 3
    private var RECEIVE_IMAGE_VIDEO = 4
    private var SEND_DOCX = 5
    private var RECEIVE_DOCX = 6

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

    class VideoHolderReceiver(bind: ItemVideoMessageReceiverBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    class DocxHolder(bind: ItemDocxMessageBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    class DocxHolderReceiver(bind: ItemDocxMessageReceiverBinding) :
        RecyclerView.ViewHolder(bind.root) {
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

            RECEIVE_IMAGE_VIDEO -> {
                val bind =
                    ItemVideoMessageReceiverBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return VideoHolderReceiver(bind)
            }

            RECEIVE_DOCX -> {
                val bind =
                    ItemDocxMessageReceiverBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return DocxHolderReceiver(bind)
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
                    if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                        holder1.binding.rlPlayContainer.visibility = View.GONE
                    } else {
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                    }
                    Glide.with(holder1.itemView.context).load(list[position].message)
                        .into(ivImageMessage)
                }
            }
            SEND_DOCX->{
                val holder1=holder as DocxHolder
                with(holder.binding){
                    tvDocName.text="File"
                }
            }

            RECEIVE_DOCX->{
                val holder1=holder as DocxHolderReceiver
                with(holder.binding){
                    tvDocName.text="File"
                }
            }

            RECEIVE_IMAGE_VIDEO -> {
                val holder1 = holder as VideoHolderReceiver
                with(holder1.binding) {
                    if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                        holder1.binding.rlPlayContainer.visibility = View.GONE
                    } else {
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                    }
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
            if (list[position].chatType == Keys.FILE_TYPE_VIDEO || list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                return RECEIVE_IMAGE_VIDEO
            } else if (list[position].chatType == Keys.FILE_TYPE_FILE) {
                return RECEIVE_DOCX
                }
            return RECIEVE_TEXT
        }
    }

}