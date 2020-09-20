package com.example.jetpack.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharePreferenceUtil {

    companion object {

        private const val PREF_TIME = "pref_time"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharePreferenceUtil? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharePreferenceUtil =
            instance ?: synchronized(LOCK) {
                instance ?: buildUtil(context).also {
                    instance = it
                }
            }

        private fun buildUtil(context: Context): SharePreferenceUtil {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharePreferenceUtil()
        }
    }

    fun saveUpdateTime(time: Long) {
        prefs?.let {
            it.edit().putLong(PREF_TIME, time).apply()
        }
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)
}