package com.example.patternclinic.selectTeam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivitySelectPatternPlusTeamBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.fragments.SelectTeamFragment
import com.example.patternclinic.utils.Keys
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPatternPlusTeam : BaseActivity() {
    var bundle: Bundle? = null
    lateinit var binding: ActivitySelectPatternPlusTeamBinding

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_pattern_plus_team)
        initDesign()
    }

    private fun initDesign() {
        val c1 = findViewById<MaterialCardView>(R.id.cv_provider)
        val c2 = findViewById<MaterialCardView>(R.id.cv_coach)
        c1.setOnClickListener {

            bundle = Bundle()
            bundle!!.putString(Keys.PROVIDERS, "1")
            val provider = SelectTeamFragment()
            provider.arguments = bundle
            provider.show(supportFragmentManager, "tag")
        }
        c2.setOnClickListener {
            bundle = Bundle()
            bundle!!.putString(Keys.COACHES, "2")
            val provider = SelectTeamFragment()
            provider.arguments = bundle
            provider.show(supportFragmentManager, "tag2")
        }
        binding.btnSubmit.setOnClickListener {
            binding.request.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding.request.visibility = View.GONE
                binding.approve.visibility = View.VISIBLE
            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding.approve.visibility = View.GONE
                binding.decline.visibility = View.VISIBLE
            }, 2000)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding.decline.visibility = View.GONE
                startActivity(Intent(this, HomeScreenActivity::class.java))
            }, 3000)
        }
    }
}