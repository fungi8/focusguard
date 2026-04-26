package com.focusguard.domain

import com.focusguard.domain.surfaces.AccessibilitySurfaceContext
import com.focusguard.domain.surfaces.RedditSurfaceMatcher
import com.focusguard.domain.surfaces.YouTubeSurfaceMatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SurfaceMatcherTest {
    @Test
    fun youtubeShortsMatchesVisibleText() {
        val match = YouTubeSurfaceMatcher().match(
            AccessibilitySurfaceContext(
                packageName = "com.google.android.youtube",
                visibleText = listOf("Shorts", "Subscribe")
            )
        )
        assertEquals("shorts", match?.surfaceId)
    }

    @Test
    fun normalYouTubeDoesNotMatchShorts() {
        val match = YouTubeSurfaceMatcher().match(
            AccessibilitySurfaceContext(
                packageName = "com.google.android.youtube",
                visibleText = listOf("Subscriptions", "Search")
            )
        )
        assertNull(match)
    }

    @Test
    fun redditPopularMatches() {
        val match = RedditSurfaceMatcher().match(
            AccessibilitySurfaceContext(
                packageName = "com.reddit.frontpage",
                visibleText = listOf("Popular", "Home")
            )
        )
        assertEquals("popular", match?.surfaceId)
    }

    @Test
    fun redditAllMatches() {
        val match = RedditSurfaceMatcher().match(
            AccessibilitySurfaceContext(
                packageName = "com.reddit.frontpage",
                visibleText = listOf("All")
            )
        )
        assertEquals("all", match?.surfaceId)
    }

    @Test
    fun redditJoinedCommunityDoesNotMatchTrapSurface() {
        val match = RedditSurfaceMatcher().match(
            AccessibilitySurfaceContext(
                packageName = "com.reddit.frontpage",
                visibleText = listOf("r/androiddev", "Search")
            )
        )
        assertNull(match)
    }
}
