package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patternclinic.R
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.google.android.material.button.MaterialButton

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        val submit = findViewById<MaterialButton>(R.id.btn_submit_reset)
        submit.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}