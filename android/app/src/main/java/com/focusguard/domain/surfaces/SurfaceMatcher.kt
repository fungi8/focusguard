package com.focusguard.domain.surfaces

data class AccessibilitySurfaceContext(
    val packageName: String,
    val className: String? = null,
    val visibleText: List<String> = emptyList(),
    val viewIds: List<String> = emptyList(),
    val timestampMillis: Long = System.currentTimeMillis()
)

data class SurfaceMatch(
    val packageName: String,
    val appName: String,
    val surfaceId: String,
    val surfaceName: String,
    val confidence: Float,
    val evidence: List<String>
)

interface SurfaceMatcher {
    fun match(context: AccessibilitySurfaceContext): SurfaceMatch?
}

class CompositeSurfaceMatcher(
    private val matchers: List<SurfaceMatcher> = listOf(
        YouTubeSurfaceMatcher(),
        RedditSurfaceMatcher()
    )
) : SurfaceMatcher {
    override fun match(context: AccessibilitySurfaceContext): SurfaceMatch? {
        return matchers.firstNotNullOfOrNull { it.match(context) }
    }
}
