package com.example.patternclinic.home.optimalFitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityOptimalFitnessBinding

class OptimalFitnessActivity : AppCompatActivity() {
    var binding:ActivityOptimalFitnessBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=DataBindingUtil.setContentView(this,R.layout.activity_optimal_fitness)
        initDesign()
    }

    private fun initDesign() {
        binding!!.toolBarOptimal.tvTitle.text = getString(R.string.optimal_fitness)
        binding!!.toolBarOptimal.ivBack.visibility = View.VISIBLE
        binding!!.toolBarOptimal.ivBack.setOnClickListener {
            finish()
        }

    }
}