package com.example.patternclinic.data.model

data class UploadFileResponse(
    val errorMessage: String,
    val imageurls: List<Imageurl>,
    val response: Int
)

data class Imageurl(
    val files: String,
    val filetype: String
)