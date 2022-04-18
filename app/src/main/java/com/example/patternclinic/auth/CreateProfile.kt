package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityCreateProfileBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class CreateProfile : BaseActivity() {
    companion object {
        lateinit var binding: ActivityCreateProfileBinding
    }

    val updateProfileViewModel: UpdateProfileViewModel by viewModels()

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile)
        binding.viewModel = updateProfileViewModel
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }

    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}