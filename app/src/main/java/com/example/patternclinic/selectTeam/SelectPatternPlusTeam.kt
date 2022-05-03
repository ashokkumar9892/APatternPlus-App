package com.example.patternclinic.selectTeam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.DoctorInfo
import com.example.patternclinic.databinding.ActivitySelectPatternPlusTeamBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.fragments.SelectTeamFragment
import com.example.patternclinic.selectTeam.fragments.SelectTeamViewModel
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPatternPlusTeam : BaseActivity() {
    var map = HashMap<String, Any>()
    var bundle: Bundle? = null

    val selectTeamViewModel: SelectTeamViewModel by viewModels()

    companion object {
        var doctorInfo: DoctorInfo? = null
        var coachInfo: DoctorInfo? = null
        lateinit var binding: ActivitySelectPatternPlusTeamBinding
    }

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_pattern_plus_team)
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
            binding.request.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.request.visibility = View.GONE
                binding.approve.visibility = View.VISIBLE
            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.approve.visibility = View.GONE
                binding.decline.visibility = View.VISIBLE
            }, 2000)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.decline.visibility = View.GONE
                startActivity(Intent(this, HomeScreenActivity::class.java))
            }, 3000)
        }


        binding.btnSubmit.setOnClickListener {
            map.clear()
            map[ApiConstants.APIParams.SK.value] = SharedPrefs.getLoggedInUser()!!.patientInfo.sk
            map[ApiConstants.APIParams.DOCTOR_ID.value] = doctorInfo!!.sk
            map[ApiConstants.APIParams.DOCTOR_NAME.value] = doctorInfo!!.userName
            map[ApiConstants.APIParams.COACH_ID.value] = coachInfo!!.sk
            map[ApiConstants.APIParams.COACH_NAME.value] = coachInfo!!.userName
            map[ApiConstants.APIParams.COUNTRY.value] = binding.ccp.selectedCountryName.toString()
            map[ApiConstants.APIParams.AUTH_TOKEN.value] = SharedPrefs.getLoggedInUser()!!.authToken
            selectTeamViewModel.selectTeamApi(map)

        }
    }
}