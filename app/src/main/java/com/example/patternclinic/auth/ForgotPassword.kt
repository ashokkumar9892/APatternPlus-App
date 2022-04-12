package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.patternclinic.R
import com.google.android.material.imageview.ShapeableImageView

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val submit = findViewById<TextView>(R.id.tv_submit_forgot)
        val back = findViewById<ShapeableImageView>(R.id.iv_back_forgot)
        back.setOnClickListener {
            finish()
        }

        submit.setOnClickListener {
            startActivity(Intent(this, ResetPassword::class.java))
        }
    }
}