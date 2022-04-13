package com.example.patternclinic.home.progressTracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityApScoreBinding
import com.example.patternclinic.home.nutrition.RecipeDetailActivity

class ApScoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityApScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ap_score)
        initDesign()
    }

    private fun initDesign() {
        binding.toolBar.tvTitle.text = getString(R.string.ap_score_title)
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }

    }
}