package com.example.patternclinic.selectTeam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.patternclinic.R
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.fragments.SelectTeamFragment
import com.google.android.material.card.MaterialCardView

class SelectPatternPlusTeam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pattern_plus_team)
        initDesign()
    }

    private fun initDesign() {
        val c1 = findViewById<MaterialCardView>(R.id.cv_provider)
        val c2 = findViewById<MaterialCardView>(R.id.cv_coach)
        c1.setOnClickListener {
           SelectTeamFragment().show(supportFragmentManager,"KK")
        }
        c2.setOnClickListener {
            SelectTeamFragment().show(supportFragmentManager,"KK")
        }
    }
}