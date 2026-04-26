package com.focusguard.service

import android.content.Context

data class DetectionSnapshot(
    val lastPackageName: String?,
    val lastClassName: String?,
    val lastTextSample: String?,
    val lastMatch: String?,
    val updatedAtMillis: Long
)

class DetectionDiagnostics(context: Context) {
    private val prefs = context.getSharedPreferences("focusguard_detection", Context.MODE_PRIVATE)

    fun recordEvent(packageName: String, className: String?, textSample: String?) {
        prefs.edit()
            .putString(KEY_PACKAGE, packageName)
            .putString(KEY_CLASS, className)
            .putString(KEY_TEXT, textSample)
            .putLong(KEY_UPDATED_AT, System.currentTimeMillis())
            .apply()
    }

    fun recordMatch(match: String) {
        prefs.edit()
            .putString(KEY_MATCH, match)
            .putLong(KEY_UPDATED_AT, System.currentTimeMillis())
            .apply()
    }

    fun snapshot(): DetectionSnapshot {
        return DetectionSnapshot(
            lastPackageName = prefs.getString(KEY_PACKAGE, null),
            lastClassName = prefs.getString(KEY_CLASS, null),
            lastTextSample = prefs.getString(KEY_TEXT, null),
            lastMatch = prefs.getString(KEY_MATCH, null),
            updatedAtMillis = prefs.getLong(KEY_UPDATED_AT, 0L)
        )
    }

    private companion object {
        const val KEY_PACKAGE = "last_package"
        const val KEY_CLASS = "last_class"
        const val KEY_TEXT = "last_text"
        const val KEY_MATCH = "last_match"
        const val KEY_UPDATED_AT = "updated_at"
    }
}
