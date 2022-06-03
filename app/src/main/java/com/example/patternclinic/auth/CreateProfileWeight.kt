package com.example.patternclinic.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityCreateProfileWeightBinding
import com.example.patternclinic.home.drawerFragments.myProfile.MyProfileViewModel
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileWeight : AppCompatActivity() {
    lateinit var binding: ActivityCreateProfileWeightBinding
    var map: HashMap<String, Any> = HashMap()
    val userDetail by lazy {
        SharedPrefs.getLoggedInUser()
    }
    val viewModel: MyProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_weight)
        if (intent.hasExtra(Keys.mapKeyProfile)) {
            binding.rlToolbar.visibility = View.GONE
            map = Gson().fromJson(Keys.updateProfileData, HashMap::class.java) as HashMap<String, Any>
            map[ApiConstants.APIParams.WEIGHT.value] = "50"
            map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "KG"
        } else {
            binding.btnNext.text = getString(R.string.submit)
            binding.rlToolbar.visibility = View.VISIBLE
            map[ApiConstants.APIParams.EMAIL.value] = userDetail?.patientInfo?.email ?: ""
            map[ApiConstants.APIParams.FIRST_NAME.value] =
                userDetail?.patientInfo?.firstName ?: ""
            map[ApiConstants.APIParams.LAST_NAME.value] = userDetail?.patientInfo?.lastName ?: ""
            map[ApiConstants.APIParams.COUNTRY.value] = userDetail?.patientInfo?.country ?: ""
            map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail?.authToken ?: ""
            map[ApiConstants.APIParams.USER_NAME.value] = userDetail?.patientInfo?.email ?: ""
            map[ApiConstants.APIParams.SK.value] = userDetail?.patientInfo?.sk ?: ""
            //todo change this after


            map[ApiConstants.APIParams.PROFILE_PIC.value] =
                userDetail?.patientInfo?.profilePic ?: ""


            map[ApiConstants.APIParams.DOB.value] = userDetail?.patientInfo?.dob ?: ""
            map[ApiConstants.APIParams.GENDER.value] =
                userDetail?.patientInfo?.gender ?: ""

            map[ApiConstants.APIParams.REFER_AS.value] = userDetail?.patientInfo?.referAs ?: ""
            map[ApiConstants.APIParams.HEIGHT.value] = userDetail?.patientInfo?.height ?: ""
            setObservers()
            initDesign()
            if (userDetail?.patientInfo?.weightUnit == "LBS") {
                binding.tvValue.text = String.format(
                    "%.2f",
                    (userDetail?.patientInfo?.weight.toString().toDouble() / 2.205).toDouble()
                )
                binding.tvUnit.text = "KG"
                map[ApiConstants.APIParams.WEIGHT.value] =
                    (userDetail?.patientInfo?.weight.toString().toDouble() / 2.205).toString()
                map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "KG"
            } else {
                binding.tvValue.text = userDetail?.patientInfo?.weight.toString()
                map[ApiConstants.APIParams.WEIGHT.value] =
                    (userDetail?.patientInfo?.weight.toString()).toString()
                map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "KG"
            }


        }



        binding.btnNext.setOnClickListener {
            if (intent.hasExtra(Keys.mapKeyProfile)) {
                Keys.updateProfileData = Gson().toJson(map)
                startActivity(
                    Intent(this, CreateProfileHeight::class.java).putExtra(Keys.mapKeyProfile,"")
                )
            } else {
                viewModel.createProfileApi(map)

            }
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
                    map[ApiConstants.APIParams.WEIGHT.value] = binding.tvValue.text.toString()
                    map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "KG"
                } else {
                    binding.tvValue.text = String.format(
                        "%.2f",
                        (binding.tvValue.text.toString().toDouble() * 2.205).toDouble()
                    )
                    binding.tvUnit.text = "LBS"
                    map[ApiConstants.APIParams.WEIGHT.value] = binding.tvValue.text.toString()
                    map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "LBS"
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
                map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "KG"
            } else {
                map.put(
                    ApiConstants.APIParams.WEIGHT.value,
                    (it.toInt() * 2.205).toDouble().toString()
                )

                val a = (it.toInt() * 2.205).toDouble()
                binding.tvValue.text = String.format("%.2f", a)
                map[ApiConstants.APIParams.WEIGHT_UNIT.value] = "LBS"
            }
        }
    }

    private fun initDesign() {
        runOnUiThread {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvTitle.text = "Current Weight"
            binding.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setObservers() {

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this)
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }
        viewModel.updateProfileResponse.observe(this) {
            if (it.response == 1) {
                val convert =
                    Gson().fromJson(Gson().toJson(it), LoginResponse::class.java)
                SharedPrefs.saveLoggedInUser(convert)
                showToast(it.errorMessage)
                finish()
            } else {
                showToast(it.errorMessage)
            }
        }
    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()
    }
}