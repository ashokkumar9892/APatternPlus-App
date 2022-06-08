package com.example.patternclinic.introScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.patternclinic.R
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.utils.changeStatusBarColorWhite
import org.w3c.dom.Text
import us.zoom.sdk.JoinMeetingParams
import us.zoom.sdk.StartMeetingParams

class IntroScreen : AppCompatActivity() {
    var pager: ViewPager? = null
    var count: TextView? = null
    var next: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_intro_screen)
        pager = findViewById<ViewPager>(R.id.viewPager)
        count = findViewById<TextView>(R.id.tv_count_video)
        next = findViewById(R.id.tv_next)
        pager!!.adapter = VideoAdapter(this)
        val skip = findViewById<TextView>(R.id.tv_skip)
        changeStatusBarColorWhite()
        skip.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        initDesign()

    }

    private fun initDesign() {
        next!!.setOnClickListener {
            if (pager!!.currentItem < 8) {
                pager!!.currentItem = pager!!.currentItem + 1
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
        pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                count!!.text = "${position + 1}/9"
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }
}