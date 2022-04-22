package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityCreateProfileHeightBinding
import com.example.patternclinic.databinding.ActivityCurrentHeightBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.Keys
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileHeight : AppCompatActivity() {
    val finalViewModel: FinalUpdateProfileViewModel by viewModels()

    companion object {
        lateinit var binding: ActivityCreateProfileHeightBinding
        lateinit var map: HashMap<String, Any>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if(intent.hasExtra(Keys.mapKeyProfile)) {
//            map = intent.extras!!.get(Keys.mapKeyProfile) as HashMap<String, Any>
//        }
        map = Gson().fromJson(Keys.updateProfileData, HashMap::class.java) as HashMap<String, Any>
        //default value
        map.put(ApiConstants.APIParams.HEIGHT.value, "50")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_height)
        binding.viewModel = finalViewModel

        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }

        binding.rulerViewHeight.setChooseValueChangeListener {

            binding.tvFeet.text = (it.toInt() / 12).toString()
            binding.tvInch.text = (it.toInt() % 12).toString()
            map.put(ApiConstants.APIParams.HEIGHT.value, it.toInt().toString())
        }
        binding.btnNext.setOnClickListener {
            finalViewModel.createProfileApi(map)
        }
    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}