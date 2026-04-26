package com.focusguard.rules

import com.focusguard.domain.boundaries.BoundaryRule
import com.focusguard.domain.boundaries.ChosenWindow
import com.focusguard.domain.boundaries.OverrideMode
import com.focusguard.domain.boundaries.SurfaceAction
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RulePackParser(
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    fun parse(raw: String): RulePack {
        val root = json.parseToJsonElement(raw).jsonObject
        val id = root.requiredString("id")
        val name = root.requiredString("name")
        val description = root.requiredString("description")
        val rules = root.requiredArray("apps").flatMap { appElement ->
            parseApp(appElement.jsonObject, id)
        }
        return RulePack(id = id, name = name, description = description, rules = rules)
    }

    fun validate(raw: String): RulePackValidationResult {
        return runCatching {
            val pack = parse(raw)
            val duplicates = pack.rules
                .groupBy { "${it.packageName}:${it.surfaceId}" }
                .filterValues { it.size > 1 }
                .keys
            if (duplicates.isEmpty()) {
                RulePackValidationResult(valid = true)
            } else {
                RulePackValidationResult(false, duplicates.map { "Duplicate surface rule: $it" })
            }
        }.getOrElse {
            RulePackValidationResult(valid = false, errors = listOf(it.message ?: "Invalid rule pack"))
        }
    }

    private fun parseApp(app: JsonObject, packId: String): List<BoundaryRule> {
        val appName = app.requiredString("app")
        val packageName = app.requiredString("package")
        return app.requiredArray("surfaces").map { surfaceElement ->
            val surface = surfaceElement.jsonObject
            val surfaceId = surface.requiredString("id")
            BoundaryRule(
                id = "$packId:$packageName:$surfaceId",
                appName = appName,
                packageName = packageName,
                surfaceId = surfaceId,
                surfaceName = surface.requiredString("name"),
                action = SurfaceAction.valueOf(surface.requiredString("action").uppercase()),
                dailyLimitMinutes = surface.optionalInt("daily_limit_minutes"),
                sessionLimitMinutes = surface.optionalInt("session_limit_minutes"),
                cooldownMinutes = surface.optionalInt("cooldown_minutes") ?: 0,
                overrideMode = OverrideMode.valueOf(
                    (surface.optionalString("override_mode") ?: "none").uppercase()
                ),
                enabled = surface.optionalBoolean("enabled") ?: true,
                chosenWindows = surface.optionalArray("chosen_windows")?.map { parseChosenWindow(it.jsonObject) }
                    ?: emptyList()
            )
        }
    }

    private fun parseChosenWindow(window: JsonObject): ChosenWindow {
        return ChosenWindow(
            id = window.requiredString("id"),
            label = window.requiredString("label"),
            startMinuteOfDay = window.requiredString("start").toMinuteOfDay(),
            endMinuteOfDay = window.requiredString("end").toMinuteOfDay(),
            daysOfWeek = window.optionalArray("days_of_week")
                ?.mapNotNull { it.jsonPrimitive.intOrNull }
                ?.toSet()
                ?: setOf(1, 2, 3, 4, 5, 6, 7),
            enabled = window.optionalBoolean("enabled") ?: true
        )
    }

    private fun String.toMinuteOfDay(): Int {
        val parts = split(":")
        require(parts.size == 2) { "Expected HH:mm time, got $this" }
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        require(hour in 0..23 && minute in 0..59) { "Invalid time: $this" }
        return hour * 60 + minute
    }

    private fun JsonObject.requiredString(key: String): String =
        get(key)?.jsonPrimitive?.content ?: error("Missing string field: $key")

    private fun JsonObject.optionalString(key: String): String? =
        get(key)?.jsonPrimitive?.content

    private fun JsonObject.optionalInt(key: String): Int? =
        get(key)?.jsonPrimitive?.intOrNull

    private fun JsonObject.optionalBoolean(key: String): Boolean? =
        get(key)?.jsonPrimitive?.booleanOrNull

    private fun JsonObject.requiredArray(key: String): JsonArray =
        get(key)?.jsonArray ?: error("Missing array field: $key")

    private fun JsonObject.optionalArray(key: String): JsonArray? =
        get(key) as? JsonArray
}
