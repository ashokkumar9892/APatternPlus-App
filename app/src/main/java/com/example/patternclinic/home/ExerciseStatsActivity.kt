package com.example.patternclinic.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityExcerciseStatsBinding

class ExerciseStatsActivity : AppCompatActivity() {
    lateinit var binding:ActivityExcerciseStatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_excercise_stats)
        initDesign()
    }
    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.exercise_stats)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}