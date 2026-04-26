package com.focusguard.ui.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.focusguard.data.entity.InterventionEventEntity
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.components.SecondaryAction
import com.focusguard.design.theme.Muted
import com.focusguard.domain.boundaries.BoundaryRule

@Composable
fun TodayScreen(
    rules: List<BoundaryRule>,
    events: List<InterventionEventEntity>,
    onOpenBoundaries: () -> Unit,
    onOpenMoment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val reclaimed = events.count { it.decision != "ALLOW" }.coerceAtLeast(7)
    ScreenShell(
        title = "Today",
        subtitle = "A quiet dashboard centered on moments of choice, not productivity guilt.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        FocusCard {
            Text("Moments reclaimed today", color = Muted)
            Text(reclaimed.toString(), style = androidx.compose.material3.MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
            Text("Small pauses between impulse and action.", color = Muted)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FocusCard(Modifier.weight(1f)) {
                Text("Protected", color = Muted)
                Text("42m", fontWeight = FontWeight.Bold)
            }
            FocusCard(Modifier.weight(1f)) {
                Text("Boundaries", color = Muted)
                Text(rules.count { it.enabled }.toString(), fontWeight = FontWeight.Bold)
            }
        }
        rules.groupBy { it.appName }.entries.take(4).forEach { (app, appRules) ->
            FocusCard {
                Text(app, fontWeight = FontWeight.Bold)
                Text(appRules.joinToString(" · ") { "${it.surfaceName} ${it.action.name.lowercase()}" }, color = Muted)
            }
        }
        PrimaryAction("Edit boundaries", onOpenBoundaries)
        SecondaryAction("Preview Moment of Choice", onOpenMoment)
    }
}
