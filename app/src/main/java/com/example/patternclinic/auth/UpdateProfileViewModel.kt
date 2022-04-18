package com.example.patternclinic.auth

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.patternclinic.utils.PermissionConstant
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.ResetPasswordResponse
import com.example.patternclinic.data.model.UpdateProfileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.grantPermission
import com.example.patternclinic.utils.imagePicker
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
    var updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    var password = ObservableField<String>()
    var email = ObservableField<String>()
    var firstName = ObservableField<String>()
    var lastName = ObservableField<String>()
    var country = ObservableField<String>()
    var dob = ObservableField<String>()

    var activity = CreateProfile.binding


    fun validate(v: View): Boolean {
//        if (password.get().isNullOrEmpty()) {
//            activity.etPassword.setError(v.context.getString(R.string.enter_password))
//            return false
//        }
//        if (confirmPassword.get().isNullOrEmpty()) {
//            activity.etConfirmPassword.setError(v.context.getString(R.string.enter_confirm_password))
//            return false
//        }
//        if (!password.get().equals(confirmPassword.get())) {
//            activity.etConfirmPassword.error = v.context.getString(R.string.password_match)
//            return false
//        }

        return true
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_user_image -> {
                v.context.grantPermission(PermissionConstant.cameraGalleryPermissionList) {
                    (v.context as AppCompatActivity).imagePicker {
                        Glide.with(v).load(it).into(activity.ivUserImage)
                    }
                }
            }

            R.id.tv_continue_create_profile -> {
                if (validate(v)) {
//                    val map = HashMap<String, Any>()
//                    map.put(ApiConstants.APIParams.RESET_PASSWORD_USER_NAME.value, ResetPassword.userName)
//                    map.put(ApiConstants.APIParams.NEW_PASSWORD.value, confirmPassword.get().toString())
//                    // TODO CHANGE
//                    map.put(ApiConstants.APIParams.CONFIRM_CODE.value, "1234")
//
//                    activity.loader.visibility = View.VISIBLE
//                    job = viewModelScope.launch {
//                        val result = getResult({ mainRepository.resetPassword(map) }, "resetPasswordApi")
//
//                        when (result) {
//                            is ResponseResult.SUCCESS -> {
//                                val response = (result.result.data as ResetPasswordResponse)
//
//                                resetResponse.postValue(response)
//                                if (response.response == 1) {
//                                    v.context.showToast(response.errorMessage)
//                                    activity.root.context.startActivity(
//                                        Intent(
//                                            activity.root.context,
//                                            LoginActivity::class.java
//                                        )
//                                    )
//                                } else {
//                                    v.context.showToast(response.errorMessage)
//                                }
//                            }
//                            is ResponseResult.ERROR -> {
////                        errorMessage.postValue(result.result.errorMsg.toString())
//                                v.context.showToast(result.result.errorMsg.toString())
//                            }
//                            is ResponseResult.FAILURE -> {
//                                v.context.showToast(result.toString())
//                            }
//                        }
//                        activity.loader.visibility = View.GONE
//                    }
//
                }

            }

        }

    }
}