package com.focusguard.domain.boundaries

import java.time.Instant
import java.time.ZoneId

class ChosenWindowChecker(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) {
    fun isInChosenWindow(rule: BoundaryRule, nowMillis: Long): Boolean {
        if (rule.chosenWindows.isEmpty()) return false
        val dateTime = Instant.ofEpochMilli(nowMillis).atZone(zoneId)
        val day = dateTime.dayOfWeek.value
        val minute = dateTime.hour * 60 + dateTime.minute

        return rule.chosenWindows.any { window ->
            window.enabled &&
                day in window.daysOfWeek &&
                minute.inWindow(window.startMinuteOfDay to window.endMinuteOfDay)
        }
    }

    private fun Int.inWindow(bounds: Pair<Int, Int>): Boolean {
        val start = bounds.first.coerceIn(0, 1439)
        val end = bounds.second.coerceIn(0, 1439)
        return if (start <= end) {
            this in start..end
        } else {
            this >= start || this <= end
        }
    }
}
