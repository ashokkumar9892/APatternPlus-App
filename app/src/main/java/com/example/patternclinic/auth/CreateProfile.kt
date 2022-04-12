package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.patternclinic.R
import com.example.patternclinic.setupDevice.ConnectDeviceActivity

class CreateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        var continueBtn=findViewById<TextView>(R.id.tv_continue_create_profile)
        continueBtn.setOnClickListener {
                startActivity(Intent(this, ConnectDeviceActivity::class.java))
        }
    }
}