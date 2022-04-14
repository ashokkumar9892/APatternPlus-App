package com.example.patternclinic.auth

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.utils.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(val mainRepository: MainRepository) : BaseViewModel() {
    var loginResponse = MutableLiveData<LoginResponse>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()


    fun validate(v: View): Boolean {
        if (email.get().isNullOrEmpty()) {

            return false
        }
        if (password.get().isNullOrEmpty()) {
            return false
        }

        return true
    }

    fun onClick(v: View) {
        if (validate(v)) {

        }
    }

}