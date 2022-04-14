package com.example.patternclinic.home.drawerFragments.myProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityYourApTeamBinding

class YourApTeamActivity : AppCompatActivity() {
    lateinit var binding:ActivityYourApTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_your_ap_team)
        initDesign()
    }
    private fun initDesign() {
        binding.toolBar.tvTitle.text = getString(R.string.your_api_team)
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }

    }
}