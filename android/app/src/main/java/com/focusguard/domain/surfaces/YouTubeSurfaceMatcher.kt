package com.focusguard.domain.surfaces

class YouTubeSurfaceMatcher : SurfaceMatcher {
    override fun match(context: AccessibilitySurfaceContext): SurfaceMatch? {
        if (context.packageName != YOUTUBE_PACKAGE) return null
        val haystack = (context.visibleText + context.viewIds + listOfNotNull(context.className))
            .joinToString(" ")
            .lowercase()

        val shortsEvidence = listOf("shorts", "reel_shelf", "shorts_video")
            .filter { it in haystack }

        return if (shortsEvidence.isNotEmpty()) {
            SurfaceMatch(
                packageName = YOUTUBE_PACKAGE,
                appName = "YouTube",
                surfaceId = "shorts",
                surfaceName = "Shorts",
                confidence = 0.76f,
                evidence = shortsEvidence
            )
        } else {
            null
        }
    }

    private companion object {
        const val YOUTUBE_PACKAGE = "com.google.android.youtube"
    }
}
