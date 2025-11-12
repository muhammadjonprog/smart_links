package core.di

import core.middleware.AnalyticsMiddleware
import core.middleware.LoggingMiddleware
import core.middleware.SecurityMiddleware
import org.koin.dsl.module

val appModules = module {
    single { LoggingMiddleware() }
    single { AnalyticsMiddleware() }
    single { SecurityMiddleware() }
}