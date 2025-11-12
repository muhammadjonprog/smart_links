package org.bot

import core.di.appModules
import core.dsl.smartLink
import core.middleware.AnalyticsMiddleware
import core.middleware.LoggingMiddleware
import core.middleware.SecurityMiddleware
import org.bot.core.rule_engine.RuleEngine
import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.RequestContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {

    //Инициализация DI
    startKoin {
        modules(appModules)
    }

    val logging = getKoin().get<LoggingMiddleware>()
    val analytics = getKoin().get<AnalyticsMiddleware>()
    val security = getKoin().get<SecurityMiddleware>()

    val rules = smartLink {
        timeRule(22, 23, "https://night.com")
        deviceRule(DeviceType.ANDROID, "https://play.google.com")
        countryRule("UK", "US", url = "https://us-site.com")
        browserRule("Chrome", "https://chrome-only.com")
    }

    val engine = RuleEngine(
        rules,
        middleware = listOf(logging, analytics, security)
    )

    val result = engine.process(
        RequestContext(
            device = DeviceType.ANDROID,
            timeHour = 23,
            country = "US",
            userAgent = "Chrome/122.0"
        )
    )

    println("Redirect to: ${result.redirectUrl} (rule: ${result.appliedRule})")

}