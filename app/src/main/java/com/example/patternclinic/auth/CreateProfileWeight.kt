package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityCreateProfileWeightBinding
import com.example.patternclinic.utils.Keys
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.veepoo.protocol.VPOperateManager

class CreateProfileWeight : AppCompatActivity() {
    lateinit var binding: ActivityCreateProfileWeightBinding
    lateinit var map: HashMap<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(Keys.mapKeyProfile)) {
            map = Gson().fromJson(intent.getStringExtra(Keys.mapKeyProfile), HashMap::class.java) as HashMap<String, Any>
        }
        map.put(ApiConstants.APIParams.WEIGHT.value, 50)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_weight)
        binding.btnNext.setOnClickListener {
            startActivity(
                Intent(this, CreateProfileHeight::class.java).putExtra(
                    Keys.mapKeyProfile,
                    map
                )
            )
        }
        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }

        binding.rulerView.setChooseValueChangeListener {
            map.put(ApiConstants.APIParams.WEIGHT.value, it.toInt())
            binding.tvValue.text = it.toInt().toString()
        }
    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}