package com.example.patternclinic.base

import android.app.Activity
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var showLoader: MutableLiveData<Boolean> = MutableLiveData()
    var onFailure: MutableLiveData<ResponseResult<ResponseWrapper<Any>>> = MutableLiveData()

    protected var job: Job? = null

    fun onbackepress(view:View){
        (view.context as Activity).onBackPressed()
    }
    override fun onCleared() {
        super.onCleared()
        if (job != null) {
            job?.cancel()
        }
    }
}