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
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener
import com.smartarmenia.dotnetcoresignalrclientjava.HubEventListener
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2
import org.json.JSONObject
import java.lang.Exception

class ChatActivity : AppCompatActivity(), HubConnectionListener, HubEventListener {
    lateinit var binding: ActivityChatBinding
    lateinit var connection: WebSocketHubConnectionP2
    var chatList= mutableListOf<String>()
    lateinit var chatAdapter:ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.ivBack.setOnClickListener {
            finish()
        }
        clicks()
        chatAdapter= ChatAdapter(chatList)
        binding.rvMessages.adapter=chatAdapter

        var url = "https://patternclinicapis.harishparas.com/ChatHub"

        connection = WebSocketHubConnectionP2(
            url, ""
        )
        connection.addListener(this)
        connection.subscribeToEvent(
            "SendMessages", this
        )
        connection.connect()


    }

    private fun clicks() {
        binding.ivSend.setOnClickListener {
            if (!binding.etMessage.text.toString().trim().isNullOrEmpty()) {
                var json = JSONObject()
                json.put(ApiConstants.APIParams.SENDER_SK.value, "PATIENT_1650001697567")
                json.put(ApiConstants.APIParams.RECEIVER_SK.value, "PATIENT_1645611610498")
                json.put(ApiConstants.APIParams.MESSAGE_TYPE.value, "Location")
                json.put(ApiConstants.APIParams.MESSAGE.value, "hello")
                json.put(
                    ApiConstants.APIParams.AUTH_TOKEN.value,
                    "eyJraWQiOiI3UGloR0p3MFlcL1dxdDRuSnMxQ0x0R2syOUZGYTZiSEhZVXpwNUY3N3Zlaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZjIxNDFkMi0wMGU0LTQ4ZDItODZlZi1hZGEwOGE3YmNlOWMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy13ZXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtd2VzdC0yXzVBMUI4dExjOSIsImNvZ25pdG86dXNlcm5hbWUiOiJhcHBzZGV2ZWxvcGVyMjJAZ21haWwuY29tIiwib3JpZ2luX2p0aSI6IjE0ZTU0NjA0LTZkYzAtNDQzZC04ODNjLWM0NmY5MmJkYjJkOSIsImF1ZCI6IjJncTBkN2k3YTZydWwwcTBhaTdsN2RzaWRzIiwiZXZlbnRfaWQiOiJjOThlYzEwZi1iNzkyLTQ5NzUtOTI2YS1iNTY3NWMyZmZlOTYiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY1MTQ3MzIyOSwiZXhwIjoxNjUxNDc2ODI5LCJpYXQiOjE2NTE0NzMyMjksImp0aSI6ImM3OGIyNTI3LTE4OTctNDk1My1iYTdhLWU4NTQ2N2IzZDllMCIsImVtYWlsIjoiYXBwc2RldmVsb3BlcjIyQGdtYWlsLmNvbSJ9.JYGTBeGisLb1sX017TvmPcxgQDo6uHJ0MYcm8gEP8lel_0Y8VprDGvkfv8TqGYBT-ZBl9rjLZoqEm0wdKAWjguNpOmOz42-kemjHgDEhAQSvRb3sivZoXVFijClHD9Bt4By-xulaWy4IBYfCeBjZzI1kMYakIgYRsdNNEj9hL1CmticTn5eklUjFKeALuEkKdRCGJ_0nj4BvSAT23pu7f_Y_wvMby8L1nCUfk6Cbf2Ap8gg-Uf2hNWnNZUx88sxksG1c98tYqjvyKAhgoVFcJ6vOyPEh8gmL9T6BOmwy-zxnyw7Dm9vYTBuHoD_bj1rD1Tjdh5MbGgqbc-v84CsFkw"
                )
                try {
//                    connection.invoke("SendMessages", Gson().toJson(json))
                    connection.invoke("SendMessages", json)
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