package com.example.patternclinic.home.drawerFragments.messages

import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.GetUserChatResponse
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.gson.Gson
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.HashMap

@AndroidEntryPoint
class ChatActivity : BaseActivity() {
    lateinit var binding: ActivityChatBinding

    lateinit var chatAdapter: ChatAdapter
    var connection2: HubConnection? = null
    val viewModel: ChatActivityViewModel by viewModels()
    var map: HashMap<String, Any>? = null
    var userDetail: LoginResponse? = null
    var receiverUser:GetUserChatResponse?=null
    var receiverSk:String?=null

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        userDetail = SharedPrefs.getLoggedInUser()
        receiverUser= Gson().fromJson(intent.getStringExtra(Keys.CHAT),GetUserChatResponse::class.java)
        receiverSk=if(receiverUser!!.senderSK!=userDetail!!.patientInfo.sk) receiverUser!!.senderSK else receiverUser!!.recieverSK


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
                        binding.loader.visibility=View.GONE
                        chatAdapter.addMessage(
                            Chatlist(
                                userDetail!!.authToken,
                                "",
                                "",
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
                        binding.loader.visibility=View.VISIBLE
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
