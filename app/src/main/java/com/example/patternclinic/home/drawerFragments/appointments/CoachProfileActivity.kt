package com.example.patternclinic.home.drawerFragments.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCoachProfileBinding

class CoachProfileActivity : AppCompatActivity() {
    var binding: ActivityCoachProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coach_profile)
        initDesign()
    }
    private fun initDesign() {
        binding!!.toolBarCoachProfile.tvTitle.text = getString(R.string.coach_profile)
        binding!!.toolBarCoachProfile.ivBack.visibility = View.VISIBLE
        binding!!.toolBarCoachProfile.ivBack.setOnClickListener {
            finish()
        }
    }
}