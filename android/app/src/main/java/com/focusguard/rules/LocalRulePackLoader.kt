package com.focusguard.rules

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRulePackLoader(
    private val context: Context,
    private val parser: RulePackParser = RulePackParser()
) {
    suspend fun loadBundledPacks(): List<RulePack> = withContext(Dispatchers.IO) {
        context.assets.list(RULE_PACK_DIR)
            ?.filter { it.endsWith(".json") }
            ?.sorted()
            ?.map { fileName ->
                context.assets.open("$RULE_PACK_DIR/$fileName").bufferedReader().use { reader ->
                    parser.parse(reader.readText())
                }
            }
            ?: emptyList()
    }

    suspend fun loadPack(id: String): RulePack? = loadBundledPacks().firstOrNull { it.id == id }

    private companion object {
        const val RULE_PACK_DIR = "rule-packs"
    }
}
