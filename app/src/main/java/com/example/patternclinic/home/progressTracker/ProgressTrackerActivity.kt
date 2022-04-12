package com.example.patternclinic.home.progressTracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityProgressTrackerBinding
import com.example.patternclinic.databinding.ActivityYourMetricsBinding

class ProgressTrackerActivity : AppCompatActivity() {
    lateinit var binding: ActivityProgressTrackerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress_tracker)
        initDesign()
    }

    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.progress_tracker)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}