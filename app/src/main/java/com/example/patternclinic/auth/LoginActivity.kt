package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.patternclinic.R
import com.example.patternclinic.utils.changeStatusBarColor
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        changeStatusBarColor(R.color.color_primary_with_opacity_8)
        val login = findViewById<MaterialButton>(R.id.btn_login)
        val forgotPassword = findViewById<TextView>(R.id.tv_forgot_password)
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        login.setOnClickListener {
            startActivity(Intent(this, CreateProfile::class.java))

        }

    }
}