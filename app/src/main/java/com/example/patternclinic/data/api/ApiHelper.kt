package com.example.patternclinic.data.api


import com.example.patternclinic.data.model.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.FieldMap

interface ApiHelper {

    suspend fun login(@FieldMap map: HashMap<String, Any>):Response<LoginResponse>
}