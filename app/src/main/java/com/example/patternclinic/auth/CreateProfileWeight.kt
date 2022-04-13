package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCreateProfileWeightBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateProfileWeight : AppCompatActivity() {
    lateinit var binding: ActivityCreateProfileWeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_weight)
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, CreateProfileHeight::class.java))
        }
        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }
    }
    private fun chatBotDialog() {
        val dialog= BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}