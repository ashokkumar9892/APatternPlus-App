package com.example.patternclinic.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

//import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return binding(inflater, container)
    }
    protected abstract fun binding(inflater: LayoutInflater, container: ViewGroup?) :View?
//    var progressDialog: ProgressDialog? = null

}