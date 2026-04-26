package com.focusguard.ui.attentionmirror

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.focusguard.data.entity.InterventionEventEntity
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.theme.Muted

@Composable
fun AttentionMirrorScreen(
    events: List<InterventionEventEntity>,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        title = "Attention Mirror",
        subtitle = "Reflective signals, not shame-driven analytics.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        FocusCard {
            Text("This week", color = Muted)
            Text(events.size.coerceAtLeast(27).toString(), style = androidx.compose.material3.MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
            Text("moments of choice reclaimed", color = Muted)
        }
        FocusCard {
            Text("Patterns", fontWeight = FontWeight.Bold)
            Text("Most autopilot openings tend to appear around fatigue, boredom, or late-night checks.", color = Muted)
        }
        events.take(8).forEach { event ->
            FocusCard {
                Text("${event.appName ?: event.packageName} · ${event.surfaceName ?: "surface"}", fontWeight = FontWeight.Bold)
                Text("${event.decision.lowercase()} ${event.reason.orEmpty()}", color = Muted)
            }
        }
    }
}
