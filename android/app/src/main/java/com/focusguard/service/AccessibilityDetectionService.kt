package com.focusguard.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.focusguard.FocusGuardApp
import com.focusguard.MomentOfChoiceActivity
import com.focusguard.domain.boundaries.BoundaryEvaluationContext
import com.focusguard.domain.boundaries.SurfaceContext
import com.focusguard.domain.intervention.InterventionDecision
import com.focusguard.domain.surfaces.AccessibilitySurfaceContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccessibilityDetectionService : AccessibilityService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val lastLaunchBySurface = mutableMapOf<String, Long>()
    private lateinit var diagnostics: DetectionDiagnostics

    override fun onCreate() {
        super.onCreate()
        diagnostics = DetectionDiagnostics(applicationContext)
    }

    override fun onServiceConnected() {
        diagnostics = DetectionDiagnostics(applicationContext)
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName?.toString() ?: return
        val root = rootInActiveWindow
        val visibleText = root?.collectText().orEmpty()
        val viewIds = root?.collectViewIds().orEmpty()
        diagnostics.recordEvent(
            packageName = packageName,
            className = event.className?.toString(),
            textSample = visibleText.take(12).joinToString(" | ").take(240)
        )
        val context = AccessibilitySurfaceContext(
            packageName = packageName,
            className = event.className?.toString(),
            visibleText = visibleText,
            viewIds = viewIds,
            timestampMillis = System.currentTimeMillis()
        )
        val container = (application as? FocusGuardApp)?.container ?: return
        val match = container.surfaceMatcher.match(context) ?: return
        diagnostics.recordMatch("${match.appName} ${match.surfaceName} (${match.evidence.joinToString()})")

        scope.launch {
            val rules = container.boundaryRepository.rules.first()
                .ifEmpty {
                    val essential = container.rulePackLoader.loadPack("essential")
                    if (essential != null) {
                        container.boundaryRepository.installRules(essential.id, essential.rules)
                        essential.rules
                    } else {
                        emptyList()
                    }
                }
            val decision = container.boundaryEngine.evaluate(
                BoundaryEvaluationContext(
                    surfaceContext = SurfaceContext(
                        packageName = match.packageName,
                        appName = match.appName,
                        surfaceId = match.surfaceId,
                        surfaceName = match.surfaceName,
                        visibleText = visibleText,
                        timestampMillis = context.timestampMillis
                    ),
                    rules = rules
                )
            )
            if (decision is InterventionDecision.Allow) return@launch

            val boundaryId = when (decision) {
                is InterventionDecision.Block -> decision.boundaryId
                is InterventionDecision.ShowIntentGate -> decision.boundaryId
                is InterventionDecision.Warn -> decision.boundaryId
                InterventionDecision.Allow -> null
            }
            val reason = when (decision) {
                is InterventionDecision.Block -> decision.reason
                is InterventionDecision.ShowIntentGate -> "intent_gate"
                is InterventionDecision.Warn -> decision.message
                InterventionDecision.Allow -> null
            }

            container.attentionMirrorRepository.logEvent(
                boundaryRuleId = boundaryId,
                packageName = match.packageName,
                appName = match.appName,
                surfaceId = match.surfaceId,
                surfaceName = match.surfaceName,
                decision = decision::class.simpleName ?: "Intervention",
                reason = reason
            )
            if (decision is InterventionDecision.Block) {
                performGlobalAction(GLOBAL_ACTION_BACK)
                SystemClock.sleep(250)
            }
            launchMomentOfChoice(match.appName, match.packageName, match.surfaceId, match.surfaceName, boundaryId, decision, reason)
        }
    }

    override fun onInterrupt() = Unit

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun launchMomentOfChoice(
        appName: String,
        packageName: String,
        surfaceId: String,
        surfaceName: String,
        boundaryId: String?,
        decision: InterventionDecision,
        reason: String?
    ) {
        val key = "$packageName:$surfaceId"
        val now = System.currentTimeMillis()
        val lastLaunch = lastLaunchBySurface[key] ?: 0L
        if (now - lastLaunch < LAUNCH_THROTTLE_MILLIS) return
        lastLaunchBySurface[key] = now

        val intent = Intent(this, MomentOfChoiceActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .putExtra(InterventionLaunch.EXTRA_BOUNDARY_ID, boundaryId)
            .putExtra(InterventionLaunch.EXTRA_PACKAGE_NAME, packageName)
            .putExtra(InterventionLaunch.EXTRA_APP_NAME, appName)
            .putExtra(InterventionLaunch.EXTRA_SURFACE_ID, surfaceId)
            .putExtra(InterventionLaunch.EXTRA_SURFACE_NAME, surfaceName)
            .putExtra(InterventionLaunch.EXTRA_DECISION, decision::class.simpleName ?: "Intervention")
            .putExtra(InterventionLaunch.EXTRA_REASON, reason)
        startActivity(intent)
    }

    private fun AccessibilityNodeInfo.collectText(maxNodes: Int = 180): List<String> {
        val output = mutableListOf<String>()
        fun walk(node: AccessibilityNodeInfo?) {
            if (node == null || output.size >= maxNodes) return
            node.text?.toString()?.takeIf { it.isNotBlank() }?.let(output::add)
            node.contentDescription?.toString()?.takeIf { it.isNotBlank() }?.let(output::add)
            repeat(node.childCount) { walk(node.getChild(it)) }
        }
        walk(this)
        return output
    }

    private fun AccessibilityNodeInfo.collectViewIds(maxNodes: Int = 180): List<String> {
        val output = mutableListOf<String>()
        fun walk(node: AccessibilityNodeInfo?) {
            if (node == null || output.size >= maxNodes) return
            node.viewIdResourceName?.takeIf { it.isNotBlank() }?.let(output::add)
            repeat(node.childCount) { walk(node.getChild(it)) }
        }
        walk(this)
        return output
    }

    private companion object {
        const val LAUNCH_THROTTLE_MILLIS = 20_000L
    }
}
