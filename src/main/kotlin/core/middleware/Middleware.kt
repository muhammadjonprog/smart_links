package core.middleware

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.RuleResult

interface Middleware {

    fun onBefore(context: RequestContext) {}

    fun onAfter(context: RequestContext, result: RuleResult) {}

}
