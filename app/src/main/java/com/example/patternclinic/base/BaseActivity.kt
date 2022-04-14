package com.example.patternclinic.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding()
    }

    protected abstract fun binding()

    val loadingDialog by lazy {
//        LoadingDialog(this@BaseActivity)
    }



}