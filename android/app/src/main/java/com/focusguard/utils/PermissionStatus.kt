package com.focusguard.utils

import android.app.AppOpsManager
import android.content.Context
import android.os.Process
import android.provider.Settings
import android.text.TextUtils
import com.focusguard.service.AccessibilityDetectionService

object PermissionStatus {
    fun isUsageAccessGranted(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun isAccessibilityEnabled(context: Context): Boolean {
        val expectedService = "${context.packageName}/${AccessibilityDetectionService::class.java.name}"
        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false
        return TextUtils.SimpleStringSplitter(':').run {
            setString(enabledServices)
            any { it.equals(expectedService, ignoreCase = true) }
        }
    }
}
