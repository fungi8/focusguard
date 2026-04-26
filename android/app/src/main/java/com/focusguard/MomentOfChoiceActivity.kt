package com.focusguard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.focusguard.design.theme.FocusGuardTheme
import com.focusguard.service.InterventionLaunch
import com.focusguard.ui.momentofchoice.MomentOfChoiceScreen
import kotlinx.coroutines.launch

class MomentOfChoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as FocusGuardApp).container
        val boundaryId = intent.getStringExtra(InterventionLaunch.EXTRA_BOUNDARY_ID)
        val packageName = intent.getStringExtra(InterventionLaunch.EXTRA_PACKAGE_NAME).orEmpty()
        val appName = intent.getStringExtra(InterventionLaunch.EXTRA_APP_NAME)
        val surfaceId = intent.getStringExtra(InterventionLaunch.EXTRA_SURFACE_ID)
        val surfaceName = intent.getStringExtra(InterventionLaunch.EXTRA_SURFACE_NAME)
        val decision = intent.getStringExtra(InterventionLaunch.EXTRA_DECISION).orEmpty()
        val reason = intent.getStringExtra(InterventionLaunch.EXTRA_REASON)

        setContent {
            FocusGuardTheme {
                MomentOfChoiceScreen(
                    surfaceName = surfaceName ?: "this feed",
                    onLeave = {
                        lifecycleScope.launch {
                            container.attentionMirrorRepository.logEvent(
                                boundaryRuleId = boundaryId,
                                packageName = packageName,
                                appName = appName,
                                surfaceId = surfaceId,
                                surfaceName = surfaceName,
                                decision = decision,
                                reason = reason,
                                userAction = "leave_for_now"
                            )
                        }
                        goHome()
                    },
                    onContinue = {
                        lifecycleScope.launch {
                            container.attentionMirrorRepository.logEvent(
                                boundaryRuleId = boundaryId,
                                packageName = packageName,
                                appName = appName,
                                surfaceId = surfaceId,
                                surfaceName = surfaceName,
                                decision = decision,
                                reason = reason,
                                userAction = "continue_with_intention"
                            )
                        }
                        finish()
                    }
                )
            }
        }
    }

    private fun goHome() {
        startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finish()
    }
}
