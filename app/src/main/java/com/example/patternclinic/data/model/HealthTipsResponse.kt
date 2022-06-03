package com.example.patternclinic.data.model

data class HealthTipsResponse(
    val errorMessage: String,
    val response: Int,
    val tips: List<Tip>
)

data class Tip(
    val description: String?,
    val image: String?,
    val sk: String?
)