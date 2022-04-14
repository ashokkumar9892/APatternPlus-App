package com.example.patternclinic.home.cardiacDetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityYourMetricsBinding
//import dagger.hilt.android.AndroidEntryPoint


class YourMetrics : AppCompatActivity() {
    lateinit var binding: ActivityYourMetricsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_your_metrics)
        initDesign()
    }


    private fun initDesign() {

        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.your_metrics)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
    }