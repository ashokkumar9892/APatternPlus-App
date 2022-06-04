package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityCreateProfileHeightBinding
import com.example.patternclinic.databinding.ActivityCurrentHeightBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileHeight : AppCompatActivity() {
    val finalViewModel: FinalUpdateProfileViewModel by viewModels()
    val userDetail by lazy {
        SharedPrefs.getLoggedInUser()
    }

    companion object {
        lateinit var binding: ActivityCreateProfileHeightBinding
        var map: HashMap<String, Any> = HashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile_height)
        binding.viewModel = finalViewModel

        if (intent.hasExtra(Keys.mapKeyProfile)) {
            map =
                Gson().fromJson(Keys.updateProfileData, HashMap::class.java) as HashMap<String, Any>
            //default value
            binding.rlToolbar.visibility = View.GONE
            map[ApiConstants.APIParams.HEIGHT.value] = "4.2"
        } else {
            binding.tvPrevious.visibility=View.GONE
            binding.btnNext.text = getString(R.string.submit)
            binding.rlToolbar.visibility = View.VISIBLE
            initDesign()
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
            map[ApiConstants.APIParams.WEIGHT_UNIT.value] =
                userDetail?.patientInfo?.weightUnit ?: ""
            map[ApiConstants.APIParams.WEIGHT.value] = userDetail?.patientInfo?.weight ?: ""
            map[ApiConstants.APIParams.HEIGHT.value] = userDetail?.patientInfo?.height ?: ""

            var split = userDetail?.patientInfo?.height?.split(".")
            binding.tvFeet.text = split!![0]
            binding.tvInch.text = split!![1]


        }



        setObservers()

        binding.tvPrevious.setOnClickListener {
            finish()
        }
        binding.tvChatBot.setOnClickListener {
            chatBotDialog()
        }

        binding.rulerViewHeight.setChooseValueChangeListener {

            binding.tvFeet.text = (it.toInt() / 12).toString()
            binding.tvInch.text = (it.toInt() % 12).toString()
            map[ApiConstants.APIParams.HEIGHT.value] =
                "${binding.tvFeet.text}.${binding.tvInch.text}"
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

    private fun initDesign() {
        binding
        binding.ivBack.visibility = View.VISIBLE
        binding.tvTitle.text = getString(R.string.current_height)
        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    private fun setObservers() {

        /**
         * observer for error-response
         */
        finalViewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        finalViewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }
        finalViewModel.updateProfileResponse.observe(this) {
            if (it.response == 1) {
                val convert =
                    Gson().fromJson(Gson().toJson(it), LoginResponse::class.java)
                SharedPrefs.saveLoggedInUser(convert)
                if (intent.hasExtra(Keys.mapKeyProfile)) {
                    if (it.patientInfo!!.coachSK.isNullOrEmpty() || it.patientInfo.doctorSK.isNullOrEmpty()) {
                        startActivity(
                            Intent(
                                this,
                                SelectPatternPlusTeam::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                this,
                                HomeScreenActivity::class.java
//                            ConnectDeviceActivity::class.java
                            )
                        )
                    }
                } else {
                    finish()

                }
                showToast(it.errorMessage)
                finish()
            } else {
                showToast(it.errorMessage)
            }
        }
    }
}