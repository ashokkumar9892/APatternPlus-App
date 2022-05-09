package com.example.patternclinic.utils


import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import com.example.patternclinic.data.model.LoginResponse
import com.google.gson.Gson


object SharedPrefs {
    val preferenceName = "PatternClinic"
    var pref: SharedPreferences? = null
    val USER_DETAIL = "USER_DETAIL"
    const val FCM_TOKEN = "fcmToken"

    init {
        pref = MyApplication.getApplicationInstance().getSharedPreferences(
            preferenceName, Context.MODE_PRIVATE
        )
    }


    /**
     * Function which will save the integer value to preference with given key
     */
    fun save(preferenceKey: String, integerValue: Int) {
        saveToPreference(
            preferenceKey,
            integerValue
        )
    }

    /**
     * Function which will save the double value to preference with given key
     */
    fun save(preferenceKey: String, doubleValue: Float) {
        saveToPreference(
            preferenceKey,
            doubleValue
        )
    }

    /**
     * Function which will save the long value to preference with given key
     */
    fun save(preferenceKey: String, longValue: Long) {
        saveToPreference(
            preferenceKey,
            longValue
        )
    }

    /**
     * Function which will save the boolean value to preference with given key
     */
    fun save(preferenceKey: String, booleanValue: Boolean) {
        saveToPreference(
            preferenceKey,
            booleanValue
        )
    }

    /**
     * Function which will save the string value to preference with given key
     */
    fun saveStr(preferenceKey: String, stringValue: String?) {
        stringValue?.let {
            saveToPreference(
                preferenceKey,
                stringValue
            )
        }
    }

    /**
     * General function to save preference value
     */
    fun save(key: String, value: Any) {
        pref?.let {
            with(it.edit()) {
                when (value) {
                    is Int -> putInt(key, value)
                    is Float -> putFloat(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                }
                apply()
            }
        }
    }

    fun clearAll() {
        pref?.let {
            with(it.edit()) {
                clear()
                apply()
            }
        }
    }


    /**
     * General function to save preference value
     */
    private fun saveToPreference(key: String, value: Any) {
        pref?.let {
            with(it.edit()) {
                when (value) {
                    is Int -> putInt(key, value)
                    is Float -> putFloat(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                }
                apply()
            }
        }
    }

    /**
     * Function which will return the integer value saved in the preference corresponds to the given preference key
     */
    fun getInt(preferenceKey: String): Int {
        return pref?.getInt(preferenceKey, 0) ?: 0
    }

    /**
     * Function which will return the float value saved in the preference corresponds to the given preference key
     */
    fun getFloat(preferenceKey: String): Float {
        return pref?.getFloat(preferenceKey, 0f) ?: 0f
    }

    /**
     * Function which will return the long value saved in the preference corresponds to the given preference key
     */
    fun getLong(preferenceKey: String): Long {
        return pref?.getLong(preferenceKey, 0L) ?: 0L
    }

    /**
     * Function which will return the boolean value saved in the preference corresponds to the given preference key
     */
    fun getBoolean(preferenceKey: String): Boolean {
        return pref?.getBoolean(preferenceKey, false) ?: false
    }

    /**
     * Function which will return the string value saved in the preference corresponds to the given preference key
     */
    fun getString(preferenceKey: String): String? {
        return pref?.getString(preferenceKey, "") ?: ""
    }

    private fun remove(preferenceKey: String) {
        pref?.let {
            with(it.edit()) {
                remove(preferenceKey)
                apply()
            }
        }
    }

    fun clearPreference() {

        pref?.let {
            with(it.edit()) {
                clear()
                apply()
            }
        }

    }



    fun saveLoggedInUser(userDetails: LoginResponse?) {
        val gson = Gson()
        save(USER_DETAIL, gson.toJson(userDetails))
    }

    fun getLoggedInUser(): LoginResponse? {
        val gson = Gson()
        return gson.fromJson(getString(USER_DETAIL), LoginResponse::class.java)
    }

    fun saveFcmToken(token: String?) {
        save(FCM_TOKEN, token!!)
    }

    fun getFcmToken(): String? {
        return getString(FCM_TOKEN)
    }

    fun getUserToken(): String {
        return "Bearer " + getLoggedInUser()?.authToken
    }


}