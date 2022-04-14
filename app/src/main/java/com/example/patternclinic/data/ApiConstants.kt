package com.example.patternclinic.data

object ApiConstants {

    const val LOGIN_API = "Basic/Login"


    const val CONNECT_TIMEOUT = 240L //seconds
    const val WRITE_TIMEOUT = 240L//seconds
    const val READ_TIMEOUT = 240L//seconds

    enum class SubTypes(val value: String) {

    }

    enum class APITypes(val value: String) {

    }

    enum class APIParams(val value: String) {
        USERNAME("Username"),
        PASSWORD("Password")


    }

}