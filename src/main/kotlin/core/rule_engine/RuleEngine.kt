package org.bot.core.rule_engine

import core.middleware.Middleware
import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.Rule
import org.bot.core.rules.base.RuleResult

//Chain of Responsibility pattern
class RuleEngine(
    private val rules: List<Rule>,
    private val middleware: List<Middleware> = emptyList()
) {
    fun process(context: RequestContext): RuleResult {

        middleware.forEach { it.onBefore(context) }

        val result = rules
            .sortedByDescending { it.priority }
            .firstOrNull { it.matches(context) }
            ?.execute(context)
            ?: RuleResult("https://default.com", "Default Rule")

        middleware.forEach { it.onAfter(context, result) }

        return result
    }
}
