package com.example.patternclinic.data.api

import android.content.Context
import android.net.Uri
import com.example.patternclinic.data.ApiConstants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File


object RequestBodyRetrofit {

    fun toRequestBodyString(value: String?): RequestBody {
        return value!!.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun toRequestBodyJson(value: JSONObject?): RequestBody {
        return value!!.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }


}