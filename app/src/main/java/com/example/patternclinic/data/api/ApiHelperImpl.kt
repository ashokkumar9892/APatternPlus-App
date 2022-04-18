package com.example.patternclinic.data.api

import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.ForgotPasswordResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.ResetPasswordResponse
import com.example.patternclinic.data.model.UpdateProfileResponse
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> =
        apiService.login(map)

    override suspend fun forgotPassword(map: HashMap<String, Any>): Response<ForgotPasswordResponse> =
        apiService.forgotPassword(map)

    override suspend fun resetPassword(map: HashMap<String, Any>): Response<ResetPasswordResponse> =
        apiService.resetPassword(map)

    override suspend fun updateProfile(map: HashMap<String, Any>): Response<UpdateProfileResponse> =  apiService.updateProfile(map)


}