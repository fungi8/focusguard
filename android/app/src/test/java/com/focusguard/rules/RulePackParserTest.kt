package com.focusguard.rules

import com.focusguard.domain.boundaries.SurfaceAction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RulePackParserTest {
    private val parser = RulePackParser()

    @Test
    fun parsesValidRulePack() {
        val pack = parser.parse(validPack)
        assertEquals("test", pack.id)
        assertEquals(2, pack.rules.size)
        assertEquals(SurfaceAction.BLOCK, pack.rules.first().action)
        assertEquals("lunch", pack.rules.first().chosenWindows.first().id)
    }

    @Test
    fun rejectsInvalidAction() {
        val result = parser.validate(validPack.replace("block", "vanish"))
        assertFalse(result.valid)
    }

    @Test
    fun rejectsMissingRequiredField() {
        val result = parser.validate(validPack.replace("\"description\":\"Test pack\",", ""))
        assertFalse(result.valid)
    }

    @Test
    fun rejectsDuplicateSurfaceIdsWithinPackage() {
        val duplicate = validPack.replace("\"id\":\"search\"", "\"id\":\"shorts\"")
        val result = parser.validate(duplicate)
        assertFalse(result.valid)
        assertTrue(result.errors.any { "Duplicate" in it })
    }

    @Test
    fun validatesPreviewData() {
        val result = parser.validate(validPack)
        assertTrue(result.valid)
    }

    private val validPack = """
        {
          "id":"test",
          "name":"Test",
          "description":"Test pack",
          "apps":[
            {
              "app":"YouTube",
              "package":"com.google.android.youtube",
              "surfaces":[
                {
                  "id":"shorts",
                  "name":"Shorts",
                  "action":"block",
                  "daily_limit_minutes":0,
                  "cooldown_minutes":30,
                  "override_mode":"type_phrase",
                  "enabled":true,
                  "chosen_windows":[{"id":"lunch","label":"Lunch","start":"12:00","end":"12:30"}]
                },
                {
                  "id":"search",
                  "name":"Search",
                  "action":"allow",
                  "enabled":true
                }
              ]
            }
          ]
        }
    """.trimIndent()
}
