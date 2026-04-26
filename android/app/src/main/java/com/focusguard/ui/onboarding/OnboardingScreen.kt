package com.focusguard.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.components.SecondaryAction
import com.focusguard.design.theme.Muted

@Composable
fun OnboardingScreen(
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        title = "Your attention firewall",
        subtitle = "FocusGuard helps you interrupt addictive feeds and restore stopping cues.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        FocusCard {
            Text("Block addictive surfaces, not useful apps", fontWeight = FontWeight.Bold)
            Text("Keep Search, DMs, and Subscriptions. Block Shorts, Reels, For You, and infinite feed loops.", color = Muted)
        }
        FocusCard {
            Text("No shame. No tracking. No ads.", fontWeight = FontWeight.Bold)
            Text("Everything stays local by default. This is built for adults, not as parental control.", color = Muted)
        }
        FocusCard {
            Text("Permissions explained clearly", fontWeight = FontWeight.Bold)
            Text("Usage access and Accessibility are used only for minimal detection and intervention logic.", color = Muted)
        }
        PrimaryAction("Get started", onDone)
        SecondaryAction("Read privacy first", onDone)
    }
}
