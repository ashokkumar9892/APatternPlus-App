package com.example.patternclinic.home.drawerFragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        initDesign()
    }
    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.about_us)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}