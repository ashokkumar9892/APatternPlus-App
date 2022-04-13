package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.patternclinic.R
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.w3c.dom.Text

class CreateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        var continueBtn=findViewById<TextView>(R.id.tv_continue_create_profile)
        continueBtn.setOnClickListener {
                startActivity(Intent(this, CreateProfileWeight::class.java))
        }
        var chatBot=findViewById<TextView>(R.id.tv_chat_bot)
        chatBot.setOnClickListener {
            chatBotDialog()
        }
    }
    private fun chatBotDialog() {
        val dialog= BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}