package com.example.patternclinic.accountability

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.patternclinic.R

class TrendsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends_acitivity)
        initDesign()
    }

    private fun initDesign() {
        val title=findViewById<TextView>(R.id.tv_title)
        val back=findViewById<ImageView>(R.id.iv_back)

        back.visibility= View.VISIBLE
        title.text = getString(R.string.trends)
        back.setOnClickListener {
            finish()
        }


    }
}