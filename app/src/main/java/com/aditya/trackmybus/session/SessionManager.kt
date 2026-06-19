package com.aditya.trackmybus.session

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "TrackMyBusPrefs"
    private const val KEY_DRIVER_ID = "driver_id"
    private const val KEY_STUDENT_ID = "student_id"
    private const val KEY_STUDENT_NAME = "student_name"
    private const val KEY_BUS_ID = "bus_id"

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    var driverId: Long
        get() = prefs?.getLong(KEY_DRIVER_ID, -1L) ?: -1L
        set(value) {
            prefs?.edit()?.putLong(KEY_DRIVER_ID, value)?.apply()
        }

    var studentId: Long
        get() = prefs?.getLong(KEY_STUDENT_ID, -1L) ?: -1L
        set(value) {
            prefs?.edit()?.putLong(KEY_STUDENT_ID, value)?.apply()
        }

    var studentName: String
        get() = prefs?.getString(KEY_STUDENT_NAME, "") ?: ""
        set(value) {
            prefs?.edit()?.putString(KEY_STUDENT_NAME, value)?.apply()
        }

    var busId: Long
        get() = prefs?.getLong(KEY_BUS_ID, -1L) ?: -1L
        set(value) {
            prefs?.edit()?.putLong(KEY_BUS_ID, value)?.apply()
        }

    fun clear() {
        prefs?.edit()?.clear()?.apply()
    }
}
