package com.focusguard.ui.sharedboundaries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.focusguard.AppContainer
import com.focusguard.design.components.FocusCard
import com.focusguard.design.components.PrimaryAction
import com.focusguard.design.components.ScreenShell
import com.focusguard.design.theme.Muted
import com.focusguard.rules.RulePack
import kotlinx.coroutines.launch

@Composable
fun SharedBoundariesScreen(
    container: AppContainer,
    modifier: Modifier = Modifier
) {
    var packs by remember { mutableStateOf<List<RulePack>>(emptyList()) }
    var selected by remember { mutableStateOf<RulePack?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        packs = container.rulePackLoader.loadBundledPacks()
        selected = packs.firstOrNull()
    }

    ScreenShell(
        title = "Shared Boundaries",
        subtitle = "Community-maintained packs, like humane AdBlock lists for attention traps.",
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        packs.forEach { pack ->
            FocusCard {
                Text(pack.name, fontWeight = FontWeight.Bold)
                Text(pack.description, color = Muted)
                Text("${pack.rules.size} surface rules", color = Muted)
            }
        }
        PrimaryAction("Install selected pack", onClick = {
            selected?.let { pack ->
                scope.launch {
                    container.boundaryRepository.installRules(pack.id, pack.rules)
                }
            }
        })
    }
}
