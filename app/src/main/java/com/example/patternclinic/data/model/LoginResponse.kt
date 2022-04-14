package com.example.patternclinic.data.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String,
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("patientInfo")
    val patientInfo: PatientInfo,
    @SerializedName("response")
    val response: Int
)

data class PatientInfo(
    @SerializedName("country")
    val country: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("profilePic")
    val profilePic: Any,
    @SerializedName("sk")
    val sk: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("weight")
    val weight: String
)