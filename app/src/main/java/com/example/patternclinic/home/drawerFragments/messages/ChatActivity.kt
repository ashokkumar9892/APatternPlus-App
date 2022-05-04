package com.example.patternclinic.home.drawerFragments.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.showToast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.microsoft.signalr.Action
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener
import com.smartarmenia.dotnetcoresignalrclientjava.HubEventListener
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2
import org.json.JSONObject
import java.lang.Exception

class ChatActivity : AppCompatActivity(), HubConnectionListener, HubEventListener {
    lateinit var binding: ActivityChatBinding
//    lateinit var connection: WebSocketHubConnectionP2
    var chatList = mutableListOf<String>()
    lateinit var chatAdapter: ChatAdapter
    var connection2: HubConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.ivBack.setOnClickListener {
            finish()
        }
        clicks()
        chatAdapter = ChatAdapter(chatList)
        binding.rvMessages.adapter = chatAdapter

        var url = "https://patternclinicapis.harishparas.com/ChatHub"
        connection2 = HubConnectionBuilder.create(url).build()

        connection2!!.start().blockingAwait()

        connection2!!.on("ReceiveMessage",object :Action1<String>{
            override fun invoke(param1: String?) {
                showToast(param1.toString())

            }
        },String::class.java)


//        connection = WebSocketHubConnectionP2(
//            url, ""
//        )
//        connection.addListener(this)
//        connection.subscribeToEvent(
//            "ReceiveMessage", this
//        )
//        connection.connect()
    }

    private fun clicks() {
        binding.ivSend.setOnClickListener {
            if (!binding.etMessage.text.toString().trim().isNullOrEmpty()) {
                var json = JSONObject()
                json.put(ApiConstants.APIParams.SENDER_SK.value, "PATIENT_1650001697567")
                json.put(ApiConstants.APIParams.RECEIVER_SK.value, "PATIENT_1645611610498")
                json.put(ApiConstants.APIParams.MESSAGE_TYPE.value, "Text")
                json.put(ApiConstants.APIParams.MESSAGE.value, "hello")
                json.put(
                    ApiConstants.APIParams.AUTH_TOKEN.value,
                    "eyJraWQiOiI3UGloR0p3MFlcL1dxdDRuSnMxQ0x0R2syOUZGYTZiSEhZVXpwNUY3N3Zlaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZjIxNDFkMi0wMGU0LTQ4ZDItODZlZi1hZGEwOGE3YmNlOWMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy13ZXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtd2VzdC0yXzVBMUI4dExjOSIsImNvZ25pdG86dXNlcm5hbWUiOiJhcHBzZGV2ZWxvcGVyMjJAZ21haWwuY29tIiwib3JpZ2luX2p0aSI6ImQ0YjA2OTUwLThiOWYtNGU2OS1hMzY5LWViOGExOWE5MzQwMyIsImF1ZCI6IjJncTBkN2k3YTZydWwwcTBhaTdsN2RzaWRzIiwiZXZlbnRfaWQiOiI0MGZmMzBmNS1jMDE2LTRmYWMtYTllMC1jOTBhZWQzZDcwZjYiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY1MTY2NjYxNCwiZXhwIjoxNjUxNjcwMjE0LCJpYXQiOjE2NTE2NjY2MTQsImp0aSI6IjExYWM4Y2RjLWM4MDUtNDA4NS1iZDMyLTY4NzZlMWM0ZjAwMCIsImVtYWlsIjoiYXBwc2RldmVsb3BlcjEyM0BnbWFpbC5jb20ifQ.UntgG1191IlCfLkoduKUCTrHNOcDyb_8ZoQ-BBmDDFWANE-4JjAia9BBUoKyjYbSShjboTVQ2_IkJ6dDzTXlGbDoqU4uVv9jlXcBRMO68iSBm7BzFfHuaWZmt-YJ_8p7mxxaNnWpmm5PLOAgniRPuvnZ1bg-WqRTKdiK766cpvnhUrtqvnvFQMrrA0VKQaDW0lZA9jNhGUuaUtMDYzzl77w6XP2bYn9Pdtnb7_taQpIz4zRh9XXKbcFNEfW4YP8Muhp3Fn2u3yS_52WgYS2xdFIlAKiKMVn2eKvEnp7twHbNlFOVQGSMrKxKxz14a2X0g00q_N5bb0WH6pMbyJ-WWA"
                )
                try {
//                    connection.invoke("SendMessages", json)
                    connection2!!.send("SendMessages", json)
//
//                    showToast(connection2!!.connectionState.toString())
////                    connection.invoke("SendMessages", json)
                    chatList.add(binding.etMessage.text.toString().trim())
                    chatAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    showToast(e.toString())
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        //connection.removeListener(this)
        //connection.unSubscribeFromEvent("SendMessages", this)
        // connection.disconnect()

    }

    override fun onConnected() {
        runOnUiThread {
            showToast("connected")
        }


    }

    override fun onDisconnected() {
        runOnUiThread {
            showToast("disconnected")
        }
    }

    override fun onMessage(message: HubMessage?) {
        runOnUiThread {
            showToast("message")
        }

    }

    override fun onError(exception: Exception?) {
        runOnUiThread {
            showToast(exception.toString())
        }
    }

    override fun onEventMessage(message: HubMessage?) {
        showToast(message.toString())
    }
}