package com.focusguard.service

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder

class UsageMonitorService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    fun queryRecentForegroundPackage(nowMillis: Long = System.currentTimeMillis()): String? {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            nowMillis - 60_000,
            nowMillis
        )
        return stats.maxByOrNull { it.lastTimeUsed }?.packageName
    }
}
