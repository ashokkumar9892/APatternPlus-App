package com.example.patternclinic.data.api


import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap

interface ApiHelper {

    suspend fun login(@Body map: HashMap<String, Any>):Response<LoginResponse>

    suspend fun forgotPassword(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse>

    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse>

    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse>
    suspend fun getCoachList(@Body map: HashMap<String, Any>):Response<CoachProviderListResponse>
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse>
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse>
}