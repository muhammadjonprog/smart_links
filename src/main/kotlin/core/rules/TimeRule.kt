package core.rules

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.Rule
import org.bot.core.rules.base.RuleResult

class TimeRule(
    private val startHour: Int,
    private val endHour: Int,
    private val url: String
) : Rule {

    override val priority: Int
        get() = 10

    override val name = "TimeRule $startHour-$endHour"

    override fun matches(context: RequestContext): Boolean {
        val hour = context.timeHour
        return if (startHour <= endHour) {
            hour in startHour..endHour
        } else {
            // Диапазон “через полночь”, например 22..6
            hour >= startHour || hour <= endHour
        }
    }

    override fun execute(context: RequestContext): RuleResult {
        return RuleResult(url, name)
    }

}
