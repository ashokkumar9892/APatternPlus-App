package com.example.patternclinic.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.example.patternclinic.home.drawerFragments.appointments.settings.SettingFragment
import com.example.patternclinic.home.drawerFragments.messages.MessageFragment
import com.example.patternclinic.home.drawerFragments.personalMetrics.PersonalMetricsFragment
import com.example.patternclinic.home.nutrition.NutritionTrackingActivity
import com.example.patternclinic.home.optimalFitness.OptimalFitnessActivity
import com.example.patternclinic.home.progressTracker.ApScoreActivity
import com.example.patternclinic.home.progressTracker.ProgressTrackerActivity
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.changeStatusBarColor
import com.example.patternclinic.utils.decorStatusBar
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.inuker.bluetooth.library.channel.Code
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IBleWriteResponse
import com.veepoo.protocol.listener.data.*
import com.veepoo.protocol.model.datas.*
import com.veepoo.protocol.model.enums.EBPDetectModel
import com.veepoo.protocol.model.enums.EFunctionStatus
import com.veepoo.protocol.model.enums.ESportType
import com.veepoo.protocol.model.settings.CustomSetting
import com.veepoo.protocol.model.settings.CustomSettingData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity(), IBleWriteResponse {
    var binding: ActivityHomeScreenBinding? = null
    var isHaveMetricSystem = true
    var isMetric = true
    var is24Hour = true
    var isOpenAutoHeartDetect = true
    var isOpenAutoBpDetect = true
    var isCelsius = true
    var isOpenSportRemain = EFunctionStatus.SUPPORT
    var isOpenVoiceBpHeart = EFunctionStatus.SUPPORT
    var isOpenFindPhoneUI = EFunctionStatus.SUPPORT
    var isOpenStopWatch = EFunctionStatus.SUPPORT
    var isOpenSpo2hLowRemind = EFunctionStatus.SUPPORT
    var isOpenWearDetectSkin = EFunctionStatus.SUPPORT
    var isOpenAutoInCall = EFunctionStatus.SUPPORT
    var isOpenAutoHRV = EFunctionStatus.SUPPORT
    var isOpenDisconnectRemind = EFunctionStatus.SUPPORT
    var isAutoTemperatureDetect = EFunctionStatus.SUPPORT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(R.color.color_primary)
        decorStatusBar(false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        initDesign()
        val customSetting = CustomSetting(
            isHaveMetricSystem,
            isMetric,
            is24Hour,
            isOpenAutoHeartDetect,
            isOpenAutoBpDetect,
            isOpenSportRemain,
            isOpenVoiceBpHeart,
            isOpenFindPhoneUI,
            isOpenStopWatch,
            isOpenSpo2hLowRemind,
            isOpenWearDetectSkin,
            isOpenAutoInCall,
            isOpenAutoHRV,
            isOpenDisconnectRemind
        )
        VPOperateManager.getMangerInstance(this)
            .changeCustomSetting(this, ICustomSettingDataListener {
                showToast(it.autoHeartDetect.toString())
            },customSetting)

        binding!!.llTest.setOnClickListener {
            VPOperateManager.getMangerInstance(this).startDetectHeart(this) {
                showToast(it.toString())
            }
        }
        binding!!.ivUserImage.setOnClickListener {
//            VPOperateManager.getMangerInstance(this).readSportStep(this) {
//                showToast(it.toString())
//            }
//            VPOperateManager.getMangerInstance(this).startSportModel(this,object :ISportModelStateListener{
//                override fun onSportModelStateChange(p0: SportModelStateData?) {
//                    showToast(p0.toString())
//
//                }
//
//                override fun onSportStopped() {
//
//                }
//            })


            VPOperateManager.getMangerInstance(this).startMultSportModel(this,object :ISportModelStateListener{
                override fun onSportModelStateChange(p0: SportModelStateData?) {
                    showToast(p0.toString())

                }

                override fun onSportStopped() {

                }
            },ESportType.OUTDOOR_WALK)
//            VPOperateManager.getMangerInstance(this).startDetectBP(this,object :IBPDetectDataListener{
//                override fun onDataChange(p0: BpData?) {
//                    showToast(p0.toString())
//
//                }
//            },EBPDetectModel.DETECT_MODEL_PRIVATE)
        }
        binding!!.tvUserName.setOnClickListener {
            VPOperateManager.getMangerInstance(this).startDetectSPO2H(this) {
                showToast(it.toString())
            }
        }
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
        binding!!.layoutDrawer.tvLogOut.setOnClickListener {
            SharedPrefs.clearAll()
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()

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

    override fun onResponse(p0: Int) {
        showToast(p0.toString())
    }
}