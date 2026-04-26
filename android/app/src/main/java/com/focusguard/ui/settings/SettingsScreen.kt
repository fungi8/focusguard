package com.focusguard.ui.settings

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.components.SecondaryAction
import com.focusguard.design.theme.Muted
import com.focusguard.service.DetectionDiagnostics
import com.focusguard.service.DetectionSnapshot
import com.focusguard.utils.PermissionStatus

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val diagnostics = remember(context) { DetectionDiagnostics(context) }
    var accessibilityEnabled by remember { mutableStateOf(PermissionStatus.isAccessibilityEnabled(context)) }
    var usageAccessEnabled by remember { mutableStateOf(PermissionStatus.isUsageAccessGranted(context)) }
    var detectionSnapshot by remember { mutableStateOf(diagnostics.snapshot()) }

    fun refreshStatus() {
        accessibilityEnabled = PermissionStatus.isAccessibilityEnabled(context)
        usageAccessEnabled = PermissionStatus.isUsageAccessGranted(context)
        detectionSnapshot = diagnostics.snapshot()
    }

    LaunchedEffect(Unit) {
        refreshStatus()
    }

    ScreenShell(
        title = "Settings & privacy",
        subtitle = "Trust is product-critical. Local-first and transparent by design.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        FocusCard {
            Text("Local-first by default", fontWeight = FontWeight.Bold)
            Text("Rules, moments, and reflection data stay on-device unless the user explicitly exports them.", color = Muted)
        }
        FocusCard {
            Text("No account required", fontWeight = FontWeight.Bold)
            Text("No ads, no cloud requirement, no selling data, no default analytics.", color = Muted)
        }
        FocusCard {
            Text("Accessibility disclosure", fontWeight = FontWeight.Bold)
            Text("Used only for minimal interaction detection and intervention logic. No message reading. No keystroke capture. No accessibility data upload.", color = Muted)
        }
        FocusCard {
            Text("Required Android permissions", fontWeight = FontWeight.Bold)
            Text("Enable Usage Access and FocusGuard Surface Detection to try real app-surface interventions on your phone.", color = Muted)
            StatusRow("Accessibility", accessibilityEnabled)
            StatusRow("Usage Access", usageAccessEnabled)
        }
        PrimaryAction("Open Accessibility settings", onClick = {
            context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        })
        SecondaryAction("Open Usage Access settings", onClick = {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SecondaryAction("Open notification settings", onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                )
            })
        }
        SecondaryAction("Refresh permission and detection status", onClick = { refreshStatus() })
        FocusCard {
            Text("Detection diagnostics", fontWeight = FontWeight.Bold)
            DetectionText(detectionSnapshot)
        }
    }
}

@Composable
private fun StatusRow(label: String, enabled: Boolean) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontWeight = FontWeight.SemiBold)
        Text(if (enabled) "Enabled" else "Not enabled", color = if (enabled) com.focusguard.design.theme.DeepSage else com.focusguard.design.theme.MutedRust)
    }
}

@Composable
private fun DetectionText(snapshot: DetectionSnapshot) {
    val updated = if (snapshot.updatedAtMillis == 0L) "Never" else "${snapshot.updatedAtMillis}"
    Text("Last package: ${snapshot.lastPackageName ?: "none"}", color = Muted)
    Text("Last class: ${snapshot.lastClassName ?: "none"}", color = Muted)
    Text("Last match: ${snapshot.lastMatch ?: "none"}", color = Muted)
    Text("Text sample: ${snapshot.lastTextSample ?: "none"}", color = Muted)
    Text("Updated: $updated", color = Muted)
}
