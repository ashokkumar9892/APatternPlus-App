package com.example.patternclinic.home.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityNutritionTrackingBinding

class NutritionTrackingActivity : AppCompatActivity() {
    var binding: ActivityNutritionTrackingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_nutrition_tracking)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nutrition_tracking)
        initDesign()
    }

    private fun initDesign() {
        binding!!.toolBarNutrition.tvTitle.text = getString(R.string.nutrition_tracking)
        binding!!.toolBarNutrition.ivBack.visibility = View.VISIBLE
        binding!!.toolBarNutrition.ivBack.setOnClickListener {
            finish()
        }
        binding!!.llFoodRecipe.setOnClickListener {
            startActivity(Intent(this, RecipeDetailActivity::class.java))
        }
    }
}