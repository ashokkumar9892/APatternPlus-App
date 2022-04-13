package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCreateProfileHeightBinding
import com.example.patternclinic.databinding.ActivityCurrentHeightBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateProfileHeight : AppCompatActivity() {
    lateinit var binding:ActivityCreateProfileHeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_create_profile_height)
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, ConnectDeviceActivity::class.java))
        }
        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }
    }

    private fun chatBotDialog() {
        val dialog=BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}