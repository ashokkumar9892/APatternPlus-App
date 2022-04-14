package com.example.patternclinic.data.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(

    val authToken: String,

    val errorMessage: String,

    val patientInfo: PatientInfo,

    val response: Int
)

data class PatientInfo(

    val country: Any,

    val email: String,

    val firstName: String,

    val height: String,

    val lastName: String,

    val profilePic: Any,

    val sk: String,

    val userName: String,

    val weight: String
)