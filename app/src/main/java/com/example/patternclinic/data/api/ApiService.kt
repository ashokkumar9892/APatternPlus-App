package com.example.patternclinic.data.api

import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_API)
    suspend fun login(@FieldMap map: HashMap<String,Any>):Response<LoginResponse>
}