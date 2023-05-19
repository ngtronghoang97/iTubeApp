package com.example.itubeapp.prefmanager

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context?) {
    var PRIVATE_MODE = 0
    var pref: SharedPreferences? = null

    var editor: SharedPreferences.Editor? = null
    var SHARED_PREF_NAME = "db_pref"

    var _context: Context? = context

    // variable declaration
    var FULL_NAME = "fullName"
    var USER_NAME = "userName"

    // Constructor init
    init {
        pref = _context!!.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE)
        editor = pref!!.edit()
    }

    /**
     * start getters and setters block
     */
    @JvmName("getFULL_NAME1")
    fun getFULL_NAME(): String? {
        return if (pref!!.getString(FULL_NAME, null) != null) pref!!.getString(
            FULL_NAME,
            null
        ) else ""
    }

    @JvmName("setFULL_NAME1")
    fun setFULL_NAME(fullName: String?) {
        editor!!.putString(FULL_NAME, fullName)
        editor!!.apply()
    }

    @JvmName("getUSER_NAME1")
    fun getUSER_NAME(): String? {
        return if (pref!!.getString(USER_NAME, null) != null) pref!!.getString(
            USER_NAME,
            null
        ) else ""
    }

    @JvmName("setUSER_NAME1")
    fun setUSER_NAME(userName: String?) {
        editor!!.putString(USER_NAME, userName)
        editor!!.apply()
    }
}