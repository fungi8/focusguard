package com.focusguard.domain.surfaces

class RedditSurfaceMatcher : SurfaceMatcher {
    override fun match(context: AccessibilitySurfaceContext): SurfaceMatch? {
        if (context.packageName != REDDIT_PACKAGE) return null
        val text = context.visibleText.joinToString(" ").lowercase()
        val ids = context.viewIds.joinToString(" ").lowercase()
        val haystack = "$text $ids"

        return when {
            "popular" in haystack -> SurfaceMatch(
                packageName = REDDIT_PACKAGE,
                appName = "Reddit",
                surfaceId = "popular",
                surfaceName = "Popular",
                confidence = 0.72f,
                evidence = listOf("popular")
            )
            Regex("""\ball\b""").containsMatchIn(haystack) -> SurfaceMatch(
                packageName = REDDIT_PACKAGE,
                appName = "Reddit",
                surfaceId = "all",
                surfaceName = "All",
                confidence = 0.66f,
                evidence = listOf("all")
            )
            else -> null
        }
    }

    private companion object {
        const val REDDIT_PACKAGE = "com.reddit.frontpage"
    }
}
