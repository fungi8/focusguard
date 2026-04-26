package com.focusguard.data.model

import com.focusguard.data.entity.BoundaryRuleEntity
import com.focusguard.data.entity.ChosenWindowEntity
import com.focusguard.domain.boundaries.BoundaryRule
import com.focusguard.domain.boundaries.ChosenWindow
import com.focusguard.domain.boundaries.OverrideMode
import com.focusguard.domain.boundaries.SurfaceAction

fun BoundaryRuleEntity.toDomain(windows: List<ChosenWindowEntity>): BoundaryRule {
    return BoundaryRule(
        id = id,
        appName = appName,
        packageName = packageName,
        surfaceId = surfaceId,
        surfaceName = surfaceName,
        action = SurfaceAction.valueOf(action),
        enabled = enabled,
        dailyLimitMinutes = dailyLimitMinutes,
        sessionLimitMinutes = sessionLimitMinutes,
        cooldownMinutes = cooldownMinutes,
        overrideMode = OverrideMode.valueOf(overrideMode),
        chosenWindows = windows.filter { it.boundaryRuleId == id }.map { it.toDomain() }
    )
}

fun BoundaryRule.toEntity(sourcePackId: String? = null): BoundaryRuleEntity {
    return BoundaryRuleEntity(
        id = id,
        appName = appName,
        packageName = packageName,
        surfaceId = surfaceId,
        surfaceName = surfaceName,
        action = action.name,
        enabled = enabled,
        dailyLimitMinutes = dailyLimitMinutes,
        sessionLimitMinutes = sessionLimitMinutes,
        cooldownMinutes = cooldownMinutes,
        overrideMode = overrideMode.name,
        sourcePackId = sourcePackId
    )
}

fun ChosenWindowEntity.toDomain(): ChosenWindow {
    return ChosenWindow(
        id = id,
        label = label,
        startMinuteOfDay = startMinuteOfDay,
        endMinuteOfDay = endMinuteOfDay,
        daysOfWeek = daysOfWeekCsv.split(",").mapNotNull { it.toIntOrNull() }.toSet(),
        enabled = enabled
    )
}

fun ChosenWindow.toEntity(boundaryRuleId: String): ChosenWindowEntity {
    return ChosenWindowEntity(
        id = id,
        boundaryRuleId = boundaryRuleId,
        label = label,
        startMinuteOfDay = startMinuteOfDay,
        endMinuteOfDay = endMinuteOfDay,
        daysOfWeekCsv = daysOfWeek.joinToString(","),
        enabled = enabled
    )
}
