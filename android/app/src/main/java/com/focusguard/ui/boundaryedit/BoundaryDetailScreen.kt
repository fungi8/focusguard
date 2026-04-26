package com.focusguard.ui.boundaryedit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.LabelRow
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.theme.Muted
import com.focusguard.domain.boundaries.BoundaryRule

@Composable
fun BoundaryDetailScreen(
    rules: List<BoundaryRule>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        title = "YouTube boundary",
        subtitle = "Configure surfaces separately. This is the product's main differentiator.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        rules.forEach { rule ->
            FocusCard {
                LabelRow(rule.surfaceName, rule.action.name.replace("_", " "))
                Text(
                    when (rule.action.name) {
                        "BLOCK" -> "Hard stop with conscious override."
                        "WARN" -> "Session reminder after ${rule.sessionLimitMinutes ?: 5} minutes."
                        "ALLOW" -> "Useful path stays open."
                        else -> "Ask what brought you here."
                    },
                    color = Muted
                )
            }
        }
        FocusCard {
            Text("Conscious override", fontWeight = FontWeight.Bold)
            LabelRow("Override mode", rules.firstOrNull()?.overrideMode?.name ?: "TYPE_PHRASE")
            LabelRow("Reset window", "${rules.firstOrNull()?.cooldownMinutes ?: 30} min")
        }
        PrimaryAction("Save boundary", onBack)
    }
}
