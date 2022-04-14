package com.example.patternclinic.home.nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityRecipeDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class RecipeDetailActivity : AppCompatActivity() {
    var binding:ActivityRecipeDetailBinding?=null
    var dialog:BottomSheetDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_recipe_detail)
        initDesign()
    }

    private fun initDesign() {
        binding!!.toolBarNutrition.tvTitle.text = getString(R.string.recipe_detail)
        binding!!.toolBarNutrition.ivBack.visibility = View.VISIBLE
        binding!!.toolBarNutrition.ivBack.setOnClickListener {
            finish()
        }
        binding!!.tvCompleteRecipe.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        dialog= BottomSheetDialog(this)
        dialog!!.setContentView(R.layout.bottom_sheet_do_you_like)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.show()
    }
}