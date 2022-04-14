package com.example.patternclinic.retrofit

sealed class ResponseResult<out T> {
    data class SUCCESS<T>(val result: T) : ResponseResult<T>()
    data class ERROR<T>(val result: T) : ResponseResult<T>()
    data class FAILURE<T>(val msg: T) : ResponseResult<T>()
}
data class ResponseWrapper<T>(val data: Any?, val errorMsg: Any)