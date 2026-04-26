package com.focusguard.ui.momentofchoice

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
fun MomentOfChoiceScreen(
    surfaceName: String = "YouTube Shorts",
    onLeave: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        title = "A moment of choice",
        subtitle = "This intervention should feel humane, not punitive.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        FocusCard {
            Text("What brought you here?", fontWeight = FontWeight.Bold)
            Text("You opened $surfaceName. Before continuing, choose intentionally.", color = Muted)
        }
        listOf(
            "I came to search for something",
            "I came to message someone",
            "I came to post something",
            "I came to check one thing",
            "I came here without thinking"
        ).forEach { reason ->
            FocusCard {
                Text(reason)
            }
        }
        FocusCard {
            Text("That happens.", fontWeight = FontWeight.Bold)
            Text("Take 10 seconds. If you still want this, continue deliberately.", color = Muted)
        }
        PrimaryAction("Continue with intention", onContinue)
        SecondaryAction("Leave for now", onLeave)
        SecondaryAction("Open a better next step", onLeave)
    }
}
