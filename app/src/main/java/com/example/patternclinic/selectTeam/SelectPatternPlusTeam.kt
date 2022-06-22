package com.example.patternclinic.selectTeam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.DoctorInfo
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivitySelectPatternPlusTeamBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.fragments.SelectTeamFragment
import com.example.patternclinic.selectTeam.fragments.SelectTeamViewModel
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPatternPlusTeam : BaseActivity() {
    var map = HashMap<String, Any>()
    var bundle: Bundle? = null
    val userDetail by lazy { SharedPrefs.getLoggedInUser()!!.patientInfo }

    val selectTeamViewModel: SelectTeamViewModel by viewModels()

    companion object {
        var doctorInfo: DoctorInfo? = null
        var coachInfo: DoctorInfo? = null
        lateinit var binding: ActivitySelectPatternPlusTeamBinding
    }

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_pattern_plus_team)
        if (intent.hasExtra(Keys.UPDATE_PATTERN_TEAM)) {
            for (i in 0 until binding.spLocation.count) {
                if (binding.spLocation.getItemAtPosition(i).toString() == userDetail.teamLocation) {
                    binding.spLocation.setSelection(i)

                }
            }
            doctorInfo = DoctorInfo(
                "",
                userDetail.doctorPic ?: "",
                userDetail.doctorSK ?: "",
                userDetail.doctorName ?: ""
            )
            coachInfo = DoctorInfo(
                "",
                userDetail.coachPic ?: "",
                userDetail.coachSK ?: "",
                userDetail.coachName ?: ""
            )
            binding.rvProviderReselect.visibility = View.VISIBLE
            binding.rvCoachReselect.visibility = View.VISIBLE
            binding.tvProviderName.text = doctorInfo?.userName ?: ""
            binding.tvCoachName.text = coachInfo?.userName ?: ""
            Glide.with(this).load(doctorInfo?.profileImage ?: "")
                .placeholder(R.drawable.dummy_provider).into(binding.ivProvider)
            Glide.with(this).load(coachInfo?.profileImage ?: "")
                .placeholder(R.drawable.ic_dummy_coach).into(binding.ivCoach)
        }
        initDesign()
    }

    private fun initDesign() {
        val c1 = findViewById<MaterialCardView>(R.id.cv_provider)
        val c2 = findViewById<MaterialCardView>(R.id.cv_coach)
        c1.setOnClickListener {

            bundle = Bundle()
            bundle!!.putString(Keys.PROVIDERS, "1")
            val provider = SelectTeamFragment()
            provider.arguments = bundle
            provider.show(supportFragmentManager, "tag")
        }
        c2.setOnClickListener {
            bundle = Bundle()
            bundle!!.putString(Keys.COACHES, "2")
            val provider = SelectTeamFragment()
            provider.arguments = bundle
            provider.show(supportFragmentManager, "tag2")
        }
        selectTeamViewModel.selectTeamModel.observe(this) {
            val result = Gson().toJson(it)
            val convertResponse = Gson().fromJson<LoginResponse>(result, LoginResponse::class.java)
            SharedPrefs.saveLoggedInUser(convertResponse)
            if (intent.hasExtra(Keys.UPDATE_PATTERN_TEAM)) {
                finish()
            } else {
                    startActivity(Intent(this, HomeScreenActivity::class.java))
                    finishAffinity()
            }
        }


        binding.btnSubmit.setOnClickListener {
            if (binding.spLocation.selectedItemPosition == 0) {
                showToast("Select Location")
            } else {
                map.clear()
                map[ApiConstants.APIParams.SK.value] =
                    SharedPrefs.getLoggedInUser()!!.patientInfo.sk
                map[ApiConstants.APIParams.DOCTOR_ID.value] = doctorInfo!!.sk
                map[ApiConstants.APIParams.DOCTOR_NAME.value] = doctorInfo!!.userName
                map[ApiConstants.APIParams.COACH_ID.value] = coachInfo!!.sk
                map[ApiConstants.APIParams.COACH_NAME.value] = coachInfo!!.userName
                map[ApiConstants.APIParams.COUNTRY.value] =
                    binding.spLocation.selectedItem.toString()
                map[ApiConstants.APIParams.AUTH_TOKEN.value] =
                    SharedPrefs.getLoggedInUser()!!.authToken
                selectTeamViewModel.selectTeamApi(map)
            }
        }
    }
}