package core.middleware

import org.bot.core.rules.base.RequestContext

class SecurityMiddleware : Middleware {

    override fun onBefore(context: RequestContext) {
        if (context.country == "US") println("[SECURITY] ⚠️ Checking security...")
    }

}
