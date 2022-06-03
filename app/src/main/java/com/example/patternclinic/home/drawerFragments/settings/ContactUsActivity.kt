package com.example.patternclinic.home.drawerFragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityContactUsBinding
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsActivity : BaseActivity() {
    lateinit var binding: ActivityContactUsBinding
    val viewModel: ContactUsViewModel by viewModels()


    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        binding.viewModel=viewModel
        initDesign()
        setObservers()
    }

    private fun setObservers() {
        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this)
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }
        viewModel.contactUsResponse.observe(this){
            if(it.response==1){
                showToast(it.errorMessage)
                finish()
            }else{
                showToast(it.errorMessage)
            }
        }

    }

    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.contact_us)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
    }
}