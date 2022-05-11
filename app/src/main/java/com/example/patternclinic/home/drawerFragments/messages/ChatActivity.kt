package com.example.patternclinic.home.drawerFragments.messages

import android.media.MediaRecorder
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.RequestBodyRetrofit
import com.example.patternclinic.data.model.ChatInfo
import com.example.patternclinic.data.model.GetUserChatResponse
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.*
import com.google.gson.Gson
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.security.Key
import kotlin.collections.HashMap

@AndroidEntryPoint
class ChatActivity : BaseActivity() {
    lateinit var binding: ActivityChatBinding

    lateinit var chatAdapter: ChatAdapter
    var connection2: HubConnection? = null
    val viewModel: ChatActivityViewModel by viewModels()
    var map: HashMap<String, Any>? = null
    var userDetail: LoginResponse? = null
    var receiverUser: ChatInfo? = null
    var receiverSk: String? = null

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        userDetail = SharedPrefs.getLoggedInUser()
        receiverUser =
            Gson().fromJson(intent.getStringExtra(Keys.CHAT), ChatInfo::class.java)
        receiverSk =
//            if (receiverUser!!.senderSK != userDetail!!.patientInfo.sk) receiverUser!!.senderSK else receiverUser!!.recieverSK
           receiverUser!!.sk



        clicks()
        setObservers()
        /**
         * fetching chat data
         */
        map = HashMap()
        map!![ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        map!![ApiConstants.APIParams.SENDER_SK.value] = userDetail!!.patientInfo.sk
        map!![ApiConstants.APIParams.RECEIVER_SK.value] = receiverSk!!
        viewModel.getChat(map!!)

        connection2 = HubConnectionBuilder.create(ApiConstants.CHAT_HUB_URL)
            .build()
        connection2!!.start()

        connection2!!.on("ReceiveMessage", object : Action1<responsemodel> {
            override fun invoke(param1: responsemodel?) {

                if (param1!!.message.isNullOrEmpty()) {
                    runOnUiThread {
                        showToast(param1.messageType)
                    }

                } else {
                    runOnUiThread {
                        binding.loader.visibility = View.GONE
                        chatAdapter.addMessage(
                            Chatlist(
                                userDetail!!.authToken,
                                "",
                                param1.messageType,
                                "",
                                "",
                                false,
                                false,
                                param1.message!!,
                                "",
                                "",
                                "",
                                param1.senderSK,
                                "",
                                "",
                                "",
                                ""
                            )
                        )

                        (binding.rvMessages.layoutManager as LinearLayoutManager).scrollToPosition(
                            chatAdapter.list.size - 1
                        )
//                        binding.etMessage.setText("")
                    }
                }
            }

        }, responsemodel::class.java)


    }

    fun setObservers() {
        /**
         * observer for sign up response
         */
        viewModel.chatData.observe(this) {
            if (it.response == 1) {

                var a = it.chatlist.reversed()
                chatAdapter = ChatAdapter(a.toMutableList())
                (binding.rvMessages.layoutManager as LinearLayoutManager).stackFromEnd = true
                binding.rvMessages.adapter = chatAdapter
            } else {
                showToast(it.errorMessage)
            }
        }

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this)
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }

        /**
         * observer for upload_file response
         */
        viewModel.uploadFileResponse.observe(this) {
            if (it.response == 1) {
                val data = model(
                    userDetail!!.patientInfo.sk,
                    receiverSk!!,
                    it.imageurls[0].filetype,
                    it.imageurls[0].files ,
                    SharedPrefs.getLoggedInUser()!!.authToken
                )

                try {
                    //invoke from one connection
                    runOnUiThread {
                        connection2!!.invoke("SendMessages", data)
                        binding.etMessage.setText("")
                        binding.loader.visibility = View.VISIBLE
//
                    }

                } catch (e: Exception) {
                    showToast(e.toString())
                }
            } else {
                showToast(it.errorMessage)
            }
        }
    }


    private fun clicks() {
        binding.ivSend.setOnClickListener {
            if (!binding.etMessage.text.toString().trim().isNullOrEmpty()) {
                val data = model(
                    userDetail!!.patientInfo.sk,
                    receiverSk!!,
                    "Text",
                    binding.etMessage.text.trim().toString(),
                    SharedPrefs.getLoggedInUser()!!.authToken
                )

                try {
                    //invoke from one connection
                    runOnUiThread {
                        connection2!!.invoke("SendMessages", data)
                        binding.etMessage.setText("")
                        binding.loader.visibility = View.VISIBLE
//
                    }

                } catch (e: Exception) {
                    showToast(e.toString())
                }
            }

        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivCamera.setOnClickListener {
            grantPermission(PermissionConstant.cameraGalleryPermissionList) {
                imageVideoPicker {it,type->
                    if(type==Keys.FILE_TYPE_IMAGE) {
                        lifecycleScope.launch {
                            val file = Compressor.compress(this@ChatActivity, File(it))
                            val part = RequestBodyRetrofit.toRequestBodyFile(file.absolutePath)
                            viewModel.uploadFile(part)
                        }
                    }
                    if(type==Keys.FILE_TYPE_VIDEO){
                        var convert:Boolean?=null
                        var firsttype=it

                         convert=File(it).renameTo(File(File(it).parent,"/${File(it).name}".replace("3gp","mp4")))
                        var new: Boolean =convert

                    }


                }
            }

        }
        binding.ivDocx.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connection2!!.stop()
    }


}


data class model(
    var SenderSK: String,
    var ReceiverSK: String,
    var MessageType: String,
    var Message: String,
    var AuthToken: String
)

data class responsemodel(
    var image: String,
    var message: String?,
    var messageType: String,
    var senderSK: String,
    var senton: String
)
