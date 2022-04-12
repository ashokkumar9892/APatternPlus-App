package com.example.patternclinic.home.drawerFragments.healthTips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityHealthTipDetailBinding
import com.example.patternclinic.utils.Keys

class HealthTipDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityHealthTipDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_health_tip_detail)
        if(intent.hasExtra(Keys.FROM_CARD_TWO)){
            setDataSecondly()
        }

        initDesign()
    }

    private fun setDataSecondly() {
        binding.image.visibility=View.VISIBLE
        binding.btnSubscribe.visibility=View.GONE
        binding.ivTop.setImageResource(R.drawable.dummy_running)
    }

    private fun initDesign() {
        binding
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.health_tip_detail)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSubscribe.setOnClickListener {
            with(binding.btnSubscribe){
                if(text.toString()==getString(R.string.subscribe)){
                    text=getString(R.string.un_subscribe)
                }else{
                    text=getString(R.string.subscribe)
                }
            }
        }
    }
}