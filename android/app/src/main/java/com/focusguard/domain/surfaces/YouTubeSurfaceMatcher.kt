package com.focusguard.domain.surfaces

class YouTubeSurfaceMatcher : SurfaceMatcher {
    override fun match(context: AccessibilitySurfaceContext): SurfaceMatch? {
        if (context.packageName != YOUTUBE_PACKAGE) return null
        val haystack = (context.visibleText + context.viewIds + listOfNotNull(context.className))
            .joinToString(" ")
            .lowercase()

        val shortsEvidence = listOf(
            "shorts",
            "reel",
            "remix",
            "shorts_video",
            "shorts_shelf",
            "reel_shelf",
            "shorts_pivot"
        )
            .filter { it in haystack }

        val shortsControlsEvidence = listOf("like", "dislike", "comments", "share", "subscribe")
            .filter { token -> context.visibleText.any { it.equals(token, ignoreCase = true) } }
            .takeIf { it.size >= 4 }
            .orEmpty()

        val evidence = (shortsEvidence + shortsControlsEvidence).distinct()

        return if (evidence.isNotEmpty()) {
            SurfaceMatch(
                packageName = YOUTUBE_PACKAGE,
                appName = "YouTube",
                surfaceId = "shorts",
                surfaceName = "Shorts",
                confidence = if (shortsEvidence.isNotEmpty()) 0.82f else 0.58f,
                evidence = evidence
            )
        } else {
            null
        }
    }

    private companion object {
        const val YOUTUBE_PACKAGE = "com.google.android.youtube"
    }
}
