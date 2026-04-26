package com.focusguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Rule
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.focusguard.design.theme.FocusGuardTheme
import com.focusguard.ui.attentionmirror.AttentionMirrorScreen
import com.focusguard.ui.boundaries.BoundariesScreen
import com.focusguard.ui.boundaryedit.BoundaryDetailScreen
import com.focusguard.ui.momentofchoice.MomentOfChoiceScreen
import com.focusguard.ui.navigation.FocusGuardDestination
import com.focusguard.ui.onboarding.OnboardingScreen
import com.focusguard.ui.settings.SettingsScreen
import com.focusguard.ui.sharedboundaries.SharedBoundariesScreen
import com.focusguard.ui.today.TodayScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as FocusGuardApp).container
        setContent {
            FocusGuardTheme {
                FocusGuardAppUi(container)
            }
        }
    }
}

@Composable
private fun FocusGuardAppUi(container: AppContainer) {
    var destination by remember { mutableStateOf(FocusGuardDestination.Onboarding) }
    val rules by container.boundaryRepository.rules.collectAsState(initial = emptyList())
    val events by container.attentionMirrorRepository.observeRecentEvents().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val completed = container.preferences.onboardingCompleted.first()
        destination = if (completed) FocusGuardDestination.Today else FocusGuardDestination.Onboarding
        if (rules.isEmpty()) {
            container.rulePackLoader.loadPack("essential")?.let {
                container.boundaryRepository.installRules(it.id, it.rules)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (destination != FocusGuardDestination.Onboarding && destination != FocusGuardDestination.Moment) {
                FocusGuardBottomBar(destination) { destination = it }
            }
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (destination) {
            FocusGuardDestination.Onboarding -> OnboardingScreen(
                modifier = modifier,
                onDone = {
                    scope.launch {
                        container.preferences.setOnboardingCompleted(true)
                    }
                    destination = FocusGuardDestination.Today
                }
            )
            FocusGuardDestination.Today -> TodayScreen(
                rules = rules,
                events = events,
                modifier = modifier,
                onOpenBoundaries = { destination = FocusGuardDestination.Boundaries },
                onOpenMoment = { destination = FocusGuardDestination.Moment }
            )
            FocusGuardDestination.Boundaries -> BoundariesScreen(
                rules = rules,
                modifier = modifier,
                onOpenDetail = { destination = FocusGuardDestination.BoundaryDetail }
            )
            FocusGuardDestination.BoundaryDetail -> BoundaryDetailScreen(
                rules = rules.filter { it.appName == "YouTube" },
                modifier = modifier,
                onBack = { destination = FocusGuardDestination.Boundaries }
            )
            FocusGuardDestination.Moment -> MomentOfChoiceScreen(
                modifier = modifier,
                onLeave = { destination = FocusGuardDestination.Today },
                onContinue = { destination = FocusGuardDestination.Today }
            )
            FocusGuardDestination.Shared -> SharedBoundariesScreen(
                container = container,
                modifier = modifier
            )
            FocusGuardDestination.Mirror -> AttentionMirrorScreen(events = events, modifier = modifier)
            FocusGuardDestination.Settings -> SettingsScreen(modifier = modifier)
        }
    }
}

@Composable
private fun FocusGuardBottomBar(
    current: FocusGuardDestination,
    onSelect: (FocusGuardDestination) -> Unit
) {
    val items = listOf(
        FocusGuardDestination.Today,
        FocusGuardDestination.Boundaries,
        FocusGuardDestination.Mirror,
        FocusGuardDestination.Shared,
        FocusGuardDestination.Settings
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item == current,
                onClick = { onSelect(item) },
                icon = {
                    Icon(
                        imageVector = when (item) {
                            FocusGuardDestination.Today -> Icons.Outlined.Home
                            FocusGuardDestination.Boundaries -> Icons.AutoMirrored.Outlined.Rule
                            FocusGuardDestination.Mirror -> Icons.Outlined.Timeline
                            FocusGuardDestination.Shared -> Icons.Outlined.AutoAwesome
                            else -> Icons.Outlined.PrivacyTip
                        },
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
