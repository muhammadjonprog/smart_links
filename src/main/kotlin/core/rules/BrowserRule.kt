package core.rules

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.Rule
import org.bot.core.rules.base.RuleResult

class BrowserRule(
    private val browser: String, private val url: String
) : Rule {
    override val name = "BrowserRule"

    override fun matches(context: RequestContext): Boolean =
        context.userAgent?.contains(browser, ignoreCase = true) == true

    override fun execute(context: RequestContext): RuleResult = RuleResult(url, name)
}
