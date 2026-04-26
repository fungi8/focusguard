package com.focusguard.ui.boundaries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.LabelRow
import com.focusguard.design.components.Pill
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.theme.Muted
import com.focusguard.domain.boundaries.BoundaryRule

@Composable
fun BoundariesScreen(
    rules: List<BoundaryRule>,
    onOpenDetail: (String) -> Unit,
    onSetAppEnabled: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        title = "Boundaries",
        subtitle = "Configure the parts of apps that pull you into autopilot.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        androidx.compose.foundation.layout.Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
            Pill("Apps", selected = true)
            Pill("Surfaces")
            Pill("Chosen Windows")
        }
        rules.groupBy { it.appName }.forEach { (app, appRules) ->
            FocusCard {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(app, fontWeight = FontWeight.Bold)
                    Switch(
                        checked = appRules.any { it.enabled },
                        onCheckedChange = { onSetAppEnabled(app, it) }
                    )
                }
                Text("${appRules.size} surfaces configured", color = Muted)
                LabelRow("Hard boundaries", appRules.count { it.action.name == "BLOCK" }.toString())
                Button(onClick = { onOpenDetail(app) }) {
                    Text("Configure")
                }
            }
        }
        PrimaryAction("Configure YouTube", onClick = { onOpenDetail("YouTube") })
    }
}
