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
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse

class CreateProfileWeight : AppCompatActivity() {
    lateinit var binding: ActivityCreateProfileWeightBinding
    var map: HashMap<String, Any> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (intent.hasExtra(Keys.mapKeyProfile)) {
//            map = Gson().fromJson(intent.getStringExtra(Keys.mapKeyProfile), HashMap::class.java) as HashMap<String, Any>
//        }
        map = Gson().fromJson(Keys.updateProfileData, HashMap::class.java) as HashMap<String, Any>
        map.put(ApiConstants.APIParams.WEIGHT.value, "50")
        CreateProfileHeight.map.put(ApiConstants.APIParams.WEIGHT_UNIT.value, "KG")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_weight)
        binding.btnNext.setOnClickListener {
            Keys.updateProfileData = Gson().toJson(map)
            startActivity(
                Intent(this, CreateProfileHeight::class.java)
            )
        }
        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }
        binding.tlKgLbs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    binding.tvValue.text = String.format(
                        "%.2f",
                        (binding.tvValue.text.toString().toDouble() / 2.205).toDouble()
                    )
                    binding.tvUnit.text = "KG"
                    map.put(ApiConstants.APIParams.WEIGHT_UNIT.value, "KG")
                } else {
                    binding.tvValue.text = String.format(
                        "%.2f",
                        (binding.tvValue.text.toString().toDouble() * 2.205).toDouble()
                    )
                    binding.tvUnit.text = "LBS"
                   map.put(ApiConstants.APIParams.WEIGHT_UNIT.value, "LBS")
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.rulerView.setChooseValueChangeListener {
            if (binding.tlKgLbs.selectedTabPosition == 0) {
                map.put(ApiConstants.APIParams.WEIGHT.value, it.toInt().toString())
                binding.tvValue.text = it.toInt().toString()
            } else {
                map.put(
                    ApiConstants.APIParams.WEIGHT.value,
                    (it.toInt() * 2.205).toDouble().toString()
                )

                var a = (it.toInt() * 2.205).toDouble()
                binding.tvValue.text = String.format("%.2f", a)
            }
        }
    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}