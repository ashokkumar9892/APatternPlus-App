package com.example.patternclinic.home.drawerFragments.messages

import android.app.Activity
import android.app.ActivityOptions
import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.databinding.*
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.chatDateFormat
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Job
import java.io.File


class ChatAdapter(var list: MutableList<Chatlist>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var SEND_TEXT = 1
    private var RECIEVE_TEXT = 2
    private var SEND_IMAGE_VIDEO = 3
    private var RECEIVE_IMAGE_VIDEO = 4
    private var SEND_DOCX = 5
    private var RECEIVE_DOCX = 6
    private var SEND_AUDIO = 10
    private var RECIEVE_AUDIO = 8

    val user_id = SharedPrefs.getLoggedInUser()?.patientInfo?.sk

    class SendHolder(bind: ItemMessageSenderBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

    }

    inner class RecieveHolder(bind: ItemMessageReciveBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

        init {

        }
    }

    inner class VideoHolder(bind: ItemVideoMessageBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

        init {
            binding.root.setOnClickListener {
                Glide.with(binding.root.context).load(list[absoluteAdapterPosition].message)
                    .into(binding.ivImageMessage)
            }

            itemView.setOnClickListener {

                val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                var trans: ActivityOptions? = null
                if (list[absoluteAdapterPosition].chatType == Keys.FILE_TYPE_IMAGE) {
                    bottomDialog(absoluteAdapterPosition, this)
                } else {

                    trans = makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        binding.ivImageMessage as View?,
                        itemView.context.getString(R.string.transition_name)
                    )
                    intent.putExtra(
                        Keys.FILE_TYPE_VIDEO,
                        list[absoluteAdapterPosition].message
                    )
                    itemView.context.startActivity(intent, trans!!.toBundle())
                }

            }
        }

    }

    inner class VideoHolderReceiver(bind: ItemVideoMessageReceiverBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding = bind

        init {

            binding.root.setOnClickListener {
                Glide.with(binding.root.context).load(list[absoluteAdapterPosition].message)
                    .into(binding.ivImageMessage)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                var trans: ActivityOptions? = null
                if (list[absoluteAdapterPosition].chatType == Keys.FILE_TYPE_IMAGE) {
                    bottomDialog(absoluteAdapterPosition, this)
                } else {

                    trans = makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        binding.ivImageMessage as View?,
                        itemView.context.getString(R.string.transition_name)
                    )
                    intent.putExtra(
                        Keys.FILE_TYPE_VIDEO,
                        list[absoluteAdapterPosition].message
                    )
                    itemView.context.startActivity(intent, trans!!.toBundle())
                }

            }
        }

    }

    inner class DocxHolder(bind: ItemDocxMessageBinding) : RecyclerView.ViewHolder(bind.root) {
        var binding = bind

        init {
            itemView.setOnClickListener {
                bottomDialog(absoluteAdapterPosition, this)
            }
        }

    }

    inner class DocxHolderReceiver(bind: ItemDocxMessageReceiverBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding = bind

        init {
            itemView.setOnClickListener {
                bottomDialog(absoluteAdapterPosition, this)
            }
        }

    }


    fun bottomDialog(position: Int, holder: RecyclerView.ViewHolder) {
        val dialog = BottomSheetDialog(holder.itemView.context)
        val bind = BottomSheetOptionsBinding.inflate(LayoutInflater.from(holder.itemView.context))
        dialog.setContentView(bind.root)
        if(list[position].chatType==Keys.FILE_TYPE_VIDEO || list[position].chatType==Keys.FILE_TYPE_AUDIO){
            bind.tvDownloadOption.visibility=View.GONE
        }
        bind.tvView.setOnClickListener {
            dialog.dismiss()
            when (holder.itemViewType) {

                SEND_DOCX -> {
                    with(holder as DocxHolder) {
                        val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                        intent.putExtra(Keys.FILE_TYPE_FILE, list[absoluteAdapterPosition].message)
                        itemView.context.startActivity(intent)
                    }
                }
                RECEIVE_DOCX -> {
                    with(holder as DocxHolderReceiver) {
                        val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                        intent.putExtra(Keys.FILE_TYPE_FILE, list[absoluteAdapterPosition].message)
                        itemView.context.startActivity(intent)
                    }
                }

                RECEIVE_IMAGE_VIDEO -> {

                    with(holder as VideoHolderReceiver) {
                        val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                        var trans: ActivityOptions? = null
                        if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                            trans = makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                binding.ivImageMessage as View?,
                                itemView.context.getString(R.string.transition_name)
                            )

                            intent.putExtra(
                                Keys.FILE_TYPE_IMAGE,
                                list[absoluteAdapterPosition].message
                            )
                        } else {
                            trans = makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                binding.ivImageMessage as View?,
                                itemView.context.getString(R.string.transition_name)
                            )
                            intent.putExtra(
                                Keys.FILE_TYPE_VIDEO,
                                list[absoluteAdapterPosition].message
                            )
                        }
                        itemView.context.startActivity(intent, trans.toBundle())
                    }

                }

                SEND_IMAGE_VIDEO -> {

                    with(holder as VideoHolder) {
                        val intent = Intent(itemView.context, ViewMessageActivity::class.java)
                        var tran: ActivityOptions? = null
                        if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                            tran = makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                binding.ivImageMessage as View?,
                                itemView.context.getString(R.string.transition_name)
                            )

                            intent.putExtra(
                                Keys.FILE_TYPE_IMAGE,
                                list[absoluteAdapterPosition].message
                            )
                        } else {
                            tran = makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                binding.ivImageMessage as View?,
                                itemView.context.getString(R.string.transition_name)
                            )
                            intent.putExtra(
                                Keys.FILE_TYPE_VIDEO,
                                list[absoluteAdapterPosition].message
                            )
                        }
                        itemView.context.startActivity(intent, tran!!.toBundle())
                    }
                }
            }
        }
        bind.tvDownloadOption.setOnClickListener {
            dialog.dismiss()
            val manager =
                holder.itemView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val uri: Uri =
                Uri.parse(list[holder.absoluteAdapterPosition].message)
            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File(list[holder.absoluteAdapterPosition].message).name
            )
            val reference: Long = manager!!.enqueue(request)

        }
        dialog.show()
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
                    tvTime.text = chatDateFormat(list[position].sentOn)
                }

            }

            SEND_IMAGE_VIDEO -> {
                val holder1 = holder as VideoHolder
                with(holder1.binding) {
                    if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                        holder1.binding.rlPlayContainer.visibility = View.GONE
                    } else if (list[position].chatType == Keys.FILE_TYPE_VIDEO) {
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                        holder1.binding.ivPlay.setImageDrawable(
                            ContextCompat.getDrawable(
                                holder1.itemView.context,
                                R.drawable.play
                            )
                        )
                    } else {
                        //Audio
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                        holder1.binding.ivPlay.setImageDrawable(
                            ContextCompat.getDrawable(
                                holder1.itemView.context,
                                com.devlomi.record_view.R.drawable.recv_ic_mic
                            )
                        )
                        holder.binding.ivPlay.imageTintList = ContextCompat.getColorStateList(
                            holder1.itemView.context,
                            R.color.color_primary
                        )


                    }
                    Glide.with(holder1.itemView.context).load(list[position].message)
                        .into(ivImageMessage)
                    tvTime.text = chatDateFormat(list[position].sentOn)

                }
            }
            SEND_DOCX -> {
                val holder1 = holder as DocxHolder
                with(holder.binding) {
                    tvDocName.text = File(list[position].message).name
                    tvTime.text = chatDateFormat(list[position].sentOn)
                }

            }

            RECEIVE_DOCX -> {
                val holder1 = holder as DocxHolderReceiver
                with(holder.binding) {
                    tvDocName.text = File(list[position].message).name
                    tvTime.text = chatDateFormat(list[position].sentOn)
                }
            }
            RECEIVE_IMAGE_VIDEO -> {
                val holder1 = holder as VideoHolderReceiver
                with(holder1.binding) {
                    if (list[position].chatType == Keys.FILE_TYPE_IMAGE) {
                        holder1.binding.rlPlayContainer.visibility = View.GONE
                    } else if (list[position].chatType == Keys.FILE_TYPE_VIDEO) {
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                        holder1.binding.ivPlay.setImageDrawable(
                            ContextCompat.getDrawable(
                                holder1.itemView.context,
                                R.drawable.play
                            )
                        )
                    }else {
                        //Audio
                        holder1.binding.rlPlayContainer.visibility = View.VISIBLE
                        holder1.binding.ivPlay.setImageDrawable(
                            ContextCompat.getDrawable(
                                holder1.itemView.context,
                                com.devlomi.record_view.R.drawable.recv_ic_mic
                            )
                        )
                        holder.binding.ivPlay.imageTintList = ContextCompat.getColorStateList(
                            holder1.itemView.context,
                            R.color.color_primary
                        )


                    }
                    Glide.with(holder1.itemView.context).load(list[position].message)
                        .into(ivImageMessage)
                    tvTime.text = chatDateFormat(list[position].sentOn)
                }
            }
            RECIEVE_TEXT -> {

                val holder1 = holder as RecieveHolder
                with(holder1.binding) {
                    tvMessage.text = list[position].message
                    tvTime.text = chatDateFormat(list[position].sentOn)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].senderId == user_id) {
            if (list[position].chatType == Keys.FILE_TYPE_VIDEO || list[position].chatType == Keys.FILE_TYPE_IMAGE || list[position].chatType == Keys.FILE_TYPE_AUDIO) {
                return SEND_IMAGE_VIDEO
            } else if (list[position].chatType == Keys.FILE_TYPE_FILE) {
                return SEND_DOCX
            }

            return SEND_TEXT
        } else {
            if (list[position].chatType == Keys.FILE_TYPE_VIDEO || list[position].chatType == Keys.FILE_TYPE_IMAGE || list[position].chatType == Keys.FILE_TYPE_AUDIO) {
                return RECEIVE_IMAGE_VIDEO
            } else if (list[position].chatType == Keys.FILE_TYPE_FILE) {
                return RECEIVE_DOCX
            }

            return RECIEVE_TEXT
        }

    }


}