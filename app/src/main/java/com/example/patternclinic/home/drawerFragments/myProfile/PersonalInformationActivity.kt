package com.example.patternclinic.home.drawerFragments.myProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityPersonalInformationBinding

class PersonalInformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_information)
        initDesign()
    }

    private fun initDesign() {
        binding
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.personal_information)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}