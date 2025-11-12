package org.bot.core.rules.base

interface Rule {

    val name: String

    val priority: Int get() = 0

    fun matches(context: RequestContext): Boolean

    fun execute(context: RequestContext): RuleResult

}

