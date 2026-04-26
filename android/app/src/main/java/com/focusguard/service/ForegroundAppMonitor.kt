package com.focusguard.service

import android.app.usage.UsageStatsManager
import android.content.Context

class ForegroundAppMonitor(
    private val context: Context
) {
    fun currentPackage(nowMillis: Long = System.currentTimeMillis()): String? {
        val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        return manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, nowMillis - 60_000, nowMillis)
            .maxByOrNull { it.lastTimeUsed }
            ?.packageName
    }
}
