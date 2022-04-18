package com.example.patternclinic.data.model

data class UpdateProfileResponse(
    val AuthToken: String,
    val Country: String,
    val DOB: String,
    val Email: String,
    val FirstName: String,
    val Gender: String,
    val Height: String,
    val LastName: String,
    val ProfilePic: String,
    val ReferAs: String,
    val SK: String,
    val Weight: String
)