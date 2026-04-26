package com.focusguard.ui.settings

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.components.SecondaryAction
import com.focusguard.design.theme.Muted

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
    }
}
