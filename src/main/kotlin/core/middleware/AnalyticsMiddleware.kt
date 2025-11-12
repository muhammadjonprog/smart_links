package core.middleware

import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.RuleResult

class AnalyticsMiddleware : Middleware {

    private var counter = 0

    override fun onAfter(context: RequestContext, result: RuleResult) {
        counter++
        println("[ANALYTICS] Redirect count: $counter")
    }

    fun getCounter() = counter
}
