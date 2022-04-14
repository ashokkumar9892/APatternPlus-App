package com.example.patternclinic.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.patternclinic.R
import com.example.patternclinic.accountability.TrendsActivity
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.databinding.ActivityHomeScreenBinding
import com.example.patternclinic.databinding.BottomSheetExerciseCompletedBinding
import com.example.patternclinic.home.cardiacDetails.YourMetrics
import com.example.patternclinic.home.drawerFragments.appointments.CoachAppointments
import com.example.patternclinic.home.drawerFragments.appointments.healthTips.HealthTipsFragment
import com.example.patternclinic.home.drawerFragments.appointments.myProfile.MyProfileFragment
import com.example.patternclinic.home.nutrition.NutritionTrackingActivity
import com.example.patternclinic.home.optimalFitness.OptimalFitnessActivity
import com.example.patternclinic.home.drawerFragments.appointments.personalMetrics.PersonalMetricsFragment
import com.example.patternclinic.home.drawerFragments.appointments.settings.SettingFragment
import com.example.patternclinic.home.drawerFragments.messages.MessageFragment
import com.example.patternclinic.home.progressTracker.ApScoreActivity
import com.example.patternclinic.home.progressTracker.ProgressTrackerActivity
import com.example.patternclinic.utils.changeStatusBarColor
import com.example.patternclinic.utils.decorStatusBar
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeScreenActivity : AppCompatActivity() {
    var binding: ActivityHomeScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(R.color.color_primary)
        decorStatusBar(false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        initDesign()
    }

    private fun initDesign() {
        binding!!.ivMenu.setOnClickListener {
            binding!!.drawerLayout.openDrawer(binding!!.sideBar)
        }

        binding!!.llNutrition.setOnClickListener {
            startActivity(Intent(this, NutritionTrackingActivity::class.java))
        }
        binding!!.llAccountability.setOnClickListener {
            startActivity(Intent(this, TrendsActivity::class.java))
        }
        binding!!.llOptimalFitness.setOnClickListener {
            startActivity(Intent(this, OptimalFitnessActivity::class.java))
        }
        binding!!.layoutDrawer.ivDrawerClose.setOnClickListener {
            binding!!.drawerLayout.closeDrawer(binding!!.sideBar)
        }
        binding!!.layoutDrawer.llCoachingAppointments.setOnClickListener {

            openFragment(CoachAppointments())
        }
        binding!!.layoutDrawer.llSettings.setOnClickListener {
            openFragment(SettingFragment())

        }
        binding!!.layoutDrawer.llMyProfile.setOnClickListener {
            openFragment(MyProfileFragment())

        }
        binding!!.layoutDrawer.llHealthTips.setOnClickListener {
            openFragment(HealthTipsFragment())
        }
        binding!!.layoutDrawer.llPersonalMetrics.setOnClickListener {
            openFragment(PersonalMetricsFragment())
        }
        binding!!.layoutDrawer.llMessage.setOnClickListener {
            openFragment(MessageFragment())
        }
        binding!!.ivHome.setOnClickListener {
            startActivity(Intent(this, YourMetrics::class.java))
        }
        binding!!.rlProgressTracker.setOnClickListener {
            startActivity(Intent(this, ProgressTrackerActivity::class.java))
        }
        binding!!.tvComplete.setOnClickListener {
            openBottomDialog()
        }
        binding!!.tvViewAll.setOnClickListener {
            startActivity(Intent(this, ViewAllTodayActivity::class.java))
        }
        binding!!.includeLayout.setOnClickListener {
            startActivity(Intent(this, ExerciseStatsActivity::class.java))
        }
        binding!!.rlApScore.setOnClickListener {
            startActivity(Intent(this, ApScoreActivity::class.java))
        }
        binding!!.layoutDrawer.llHome.setOnClickListener {
//            supportFragmentManager.popBackStackImmediate()
            binding!!.drawerLayout.closeDrawer(binding!!.sideBar)
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStackImmediate()
            }

        }
        binding!!.layoutDrawer.tvLogOut.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

    }

    private fun openBottomDialog() {
        val exerciseDialog = BottomSheetDialog(this)
        val bindingDialog = BottomSheetExerciseCompletedBinding.inflate(layoutInflater)
        exerciseDialog.setContentView(bindingDialog.root)
        bindingDialog.rbDialog.setOnCheckedChangeListener { radioGroup, i ->
            when (radioGroup.checkedRadioButtonId) {
                bindingDialog.rbNo.id -> {
                    bindingDialog.llRepsContainer.visibility = View.VISIBLE
                }
                bindingDialog.rbYes.id -> {
                    bindingDialog.llRepsContainer.visibility = View.GONE
                }

            }
        }
        bindingDialog.tvSubmit.setOnClickListener {
            exerciseDialog.dismiss()
        }
        exerciseDialog.show()
    }

    private fun openFragment(frag: Fragment) {
        binding!!.drawerLayout.closeDrawer(binding!!.sideBar)
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) != frag) {
            supportFragmentManager.popBackStackImmediate()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag)
                .addToBackStack(null).commit()

        }
    }
}