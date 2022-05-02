package com.example.patternclinic.home.drawerFragments.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.showToast
import microsoft.aspnet.signalr.client.Credentials
import microsoft.aspnet.signalr.client.Platform
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent
import microsoft.aspnet.signalr.client.hubs.HubConnection

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.ivBack.setOnClickListener {
            finish()
        }
        Platform.loadPlatformComponent(AndroidPlatformComponent())
        var credential=Credentials(){
            it.addHeader("username", "username"); //get username
        }
        var url="http://patternclinicapis.harishparas.com/ChatHub"
        var hubConnection=HubConnection(url)
        hubConnection.credentials=credential
        hubConnection.connected {
            showToast("connected")
        }


    }
}