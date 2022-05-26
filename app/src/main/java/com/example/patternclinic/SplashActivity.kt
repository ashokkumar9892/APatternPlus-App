package com.example.patternclinic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.introScreen.IntroScreen
import com.example.patternclinic.utils.SharedPrefs

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (SharedPrefs.getLoggedInUser() != null) {
                startActivity(Intent(this, HomeScreenActivity::class.java))
//                startActivity(Intent(this, HomeScreenActivity::class.java))

            } else {

                startActivity(Intent(this, IntroScreen::class.java))
//            startActivity(Intent(this, OtpActivity::class.java))
//            startActivity(Intent(this, ConnectDeviceActivity::class.java))
//            startActivity(Intent(this, HomeScreenActivity::class.java))

//            startActivity(Intent(this, SelectPatternPlusTeam::class.java))
            }
            finish()

        }, 1000)
    }
}