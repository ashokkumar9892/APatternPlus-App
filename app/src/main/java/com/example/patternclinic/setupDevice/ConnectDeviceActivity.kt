package com.example.patternclinic.setupDevice

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityConnectDeviceBinding
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class ConnectDeviceActivity : AppCompatActivity() {
    var binding: ActivityConnectDeviceBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_connect_device)
        initDesign()


    }

    private fun initDesign() {
        binding!!.cvStart.setOnClickListener {
            startActivity(Intent(this, SelectPatternPlusTeam::class.java))
        }
        binding!!.llWatch.setOnClickListener {
            binding!!.layoutDisconnected.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding!!.layoutDisconnected.visibility = View.GONE
                binding!!.layoutConnected.visibility = View.VISIBLE

            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding!!.layoutConnected.visibility = View.GONE
                binding!!.llWatch.setBackgroundResource(R.drawable.drawable_shape_connect_2)
                binding!!.cvStart.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary)
//                binding!!.cvStart.setback

            }, 2000)
        }
    }
}