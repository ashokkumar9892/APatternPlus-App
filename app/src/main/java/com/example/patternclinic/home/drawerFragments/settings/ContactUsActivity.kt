package com.example.patternclinic.home.drawerFragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        initDesign()
    }

    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.contact_us)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}