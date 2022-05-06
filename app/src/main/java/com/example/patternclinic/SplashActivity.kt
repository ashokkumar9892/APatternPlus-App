package com.example.patternclinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.example.patternclinic.auth.CreateProfile
import com.example.patternclinic.auth.createProfile.OtpActivity
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.introScreen.IntroScreen
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.SharedPrefs
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.ICustomSettingDataListener
import com.veepoo.protocol.model.enums.EFunctionStatus
import com.veepoo.protocol.model.settings.CustomSetting
import com.veepoo.protocol.model.settings.CustomSettingData

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