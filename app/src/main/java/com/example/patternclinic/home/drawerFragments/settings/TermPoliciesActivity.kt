package com.example.patternclinic.home.drawerFragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityTermPoliciesBinding

class TermPoliciesActivity : AppCompatActivity() {
    lateinit var binding:ActivityTermPoliciesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_term_policies)
        initDesign()
    }
    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.term_and_policies)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}