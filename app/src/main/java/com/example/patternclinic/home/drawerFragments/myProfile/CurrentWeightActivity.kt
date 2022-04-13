package com.example.patternclinic.home.drawerFragments.myProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCurrentWeightBinding

class CurrentWeightActivity : AppCompatActivity() {
    lateinit var binding:ActivityCurrentWeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_current_weight)
        initDesign()
    }
    private fun initDesign() {
        binding
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.current_weight)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }

    }
}