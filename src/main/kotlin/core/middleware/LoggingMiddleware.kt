package core.middleware

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.RuleResult

class LoggingMiddleware : Middleware {
    override fun onBefore(context: RequestContext) {
        println("[LOG] Incoming context: $context")
    }

    override fun onAfter(context: RequestContext, result: RuleResult) {
        println("[LOG] Result: $result")
    }
}
