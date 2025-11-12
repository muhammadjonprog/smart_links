package core.rules

import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.Rule
import org.bot.core.rules.base.RuleResult

class DeviceRule(
    private val device: DeviceType,
    private val url: String
) : Rule {

    override val name = "DeviceRule $device"

    override fun matches(context: RequestContext): Boolean {
        return context.device == device
    }

    override fun execute(context: RequestContext): RuleResult {
        return RuleResult(url, name)
    }

}
