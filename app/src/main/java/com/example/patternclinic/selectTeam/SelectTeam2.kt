package com.example.patternclinic.selectTeam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivitySelectTeam2Binding
import com.example.patternclinic.home.HomeScreenActivity
import com.google.android.material.button.MaterialButton

class SelectTeam2 : AppCompatActivity() {
    lateinit var binding: ActivitySelectTeam2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_team2)

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