package core.rules

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.Rule
import org.bot.core.rules.base.RuleResult

class CountryRule(
    private val countries: List<String>,
    private val url: String
) : Rule {

    override val priority: Int
        get() = 6

    override val name = "CountryRule ${countries.joinToString()}"

    override fun matches(context: RequestContext): Boolean {
        return context.country in countries
    }

    override fun execute(context: RequestContext): RuleResult {
        return RuleResult(url, name)
    }

}
