package com.example.patternclinic.home.drawerFragments.myProfile

import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCurrentHeightBinding


class CurrentHeightActivity : AppCompatActivity() {
    private val MIN_VALUE = 5f
    private val MAX_VALUE = 33f

    lateinit var binding: ActivityCurrentHeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_height)
        initDesign()
    }
    private fun initDesign() {
        binding
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.current_height)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }

    }
}