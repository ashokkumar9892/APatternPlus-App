package com.example.patternclinic.retrofit

import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> getResult(call: suspend () -> Response<T>, apiType:String): ResponseResult<ResponseWrapper<Any>> {
    try {
        val response = call()
        if (response.isSuccessful && (response.code() == 200 || response.code() == 201)) {
            val body = response.body()
            return ResponseResult.SUCCESS(ResponseWrapper(body, ""))
        }
        if (response.code() == 400) {
            val gson = Gson()
            /*val model: BaseResponse =
                gson.fromJson(response.errorBody()?.string(), BaseResponse::class.java)*/
            return ResponseResult.ERROR(ResponseWrapper("", "error"))
        }
        if (response.code() == 422) {
           /* val gson = Gson()
            val model: BaseResponse = gson.fromJson(response.errorBody()?.string(), BaseResponse::class.java)*/
            return ResponseResult.ERROR(ResponseWrapper("", "hhhhh"))
        }
        if (response.code() == 401) {
            return ResponseResult.ERROR(ResponseWrapper("", "not authorize"))
        }
        return ResponseResult.ERROR(ResponseWrapper("", "Something went wrong, please try again"))
    } catch (e: Exception) {
        return ResponseResult.FAILURE(ResponseWrapper(apiType, e))
    }
}