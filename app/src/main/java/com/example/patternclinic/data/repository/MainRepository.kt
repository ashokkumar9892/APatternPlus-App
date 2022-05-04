package com.example.patternclinic.data.repository

import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> = apiHelper.login(map)

    suspend fun forgotPassword(@Body map: HashMap<String, Any>): Response<ForgotPasswordResponse> = apiHelper.forgotPassword(map)
    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse> = apiHelper.resetPassword(map)
    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse> =  apiHelper.updateProfile(map)
    suspend fun getCoachList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse> = apiHelper.getCoachList(map)
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse> = apiHelper.getDoctorList(map)
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse> = apiHelper.selectApTeam(map)
    suspend fun signUp(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse> = apiHelper.signUp(map)
    suspend fun sendOtp(@Body map: HashMap<String, Any>):Response<LoginResponse> = apiHelper.sendOtp(map)
}
