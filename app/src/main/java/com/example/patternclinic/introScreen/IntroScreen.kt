package com.example.patternclinic.introScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.databinding.ActivityIntroScreenBinding
import com.example.patternclinic.utils.changeStatusBarColorWhite
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text
import us.zoom.sdk.JoinMeetingParams
import us.zoom.sdk.StartMeetingParams

class IntroScreen : AppCompatActivity() {
    //    var pager: ViewPager? = null
    var count: TextView? = null
    var next: TextView? = null

    lateinit var binding: ActivityIntroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_screen)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

//        pager = findViewById<ViewPager>(R.id.viewPager)
        count = findViewById<TextView>(R.id.tv_count_video)
        next = findViewById(R.id.tv_next)
//        pager!!.adapter = VideoAdapter(this)
        val skip = findViewById<TextView>(R.id.tv_skip)

        changeStatusBarColorWhite()
        binding.tvNext.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        Glide.with(this)
            .load("android.resource://" + packageName.toString() + "/" + R.raw.intro_video)
            .into(binding.ivIntro)

    }


//    private fun initDesign() {
//        next!!.setOnClickListener {
//            if (pager!!.currentItem < 8) {
//                pager!!.currentItem = pager!!.currentItem + 1
//            }else{
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
//
//        }
//        pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                count!!.text = "${position + 1}/9"
//            }
//
//            override fun onPageSelected(position: Int) {
//
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })
//
//    }
}