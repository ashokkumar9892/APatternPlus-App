package com.example.patternclinic.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityViewAllTodayBinding

class ViewAllTodayActivity : AppCompatActivity() {
    lateinit var binding:ActivityViewAllTodayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_view_all_today)
        initDesign()
    }
    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.todays_activities)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}