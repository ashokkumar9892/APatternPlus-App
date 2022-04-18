package com.example.patternclinic.data.repository

import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.model.ForgotPasswordResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.ResetPasswordResponse
import com.example.patternclinic.data.model.UpdateProfileResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> =
        apiHelper.login(map)

    suspend fun forgotPassword(@Body map: HashMap<String, Any>): Response<ForgotPasswordResponse> =
        apiHelper.forgotPassword(map)

    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse> =
        apiHelper.resetPassword(map)

    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse> =  apiHelper.updateProfile(map)
}
