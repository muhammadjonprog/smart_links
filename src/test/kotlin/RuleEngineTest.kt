import core.middleware.AnalyticsMiddleware
import core.middleware.LoggingMiddleware
import core.rules.BrowserRule
import core.rules.CountryRule
import core.rules.DeviceRule
import core.rules.TimeRule
import org.bot.core.rule_engine.RuleEngine
import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.RequestContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class RuleEngineTest {

    @Test
    fun `should apply first matching rule`() {
        val rules = listOf(
            DeviceRule(device = DeviceType.IOS, url = "https://apple.com"),
            TimeRule(startHour = 22, endHour = 23, url = "https://night.com")
        )
        val engine = RuleEngine(rules)

        val context = RequestContext(device = DeviceType.IOS, timeHour = 15, country = "US")
        val result = engine.process(context)

        assertEquals("https://apple.com", result.redirectUrl)
    }

    @Test
    fun `should apply default rule when no match`() {
        val rules = listOf(
            TimeRule(startHour = 22, endHour = 23, url = "https://night.com")
        )
        val engine = RuleEngine(rules)

        val context = RequestContext(device = DeviceType.ANDROID, timeHour = 10, country = "UK")
        val result = engine.process(context)

        assertEquals("https://default.com", result.redirectUrl)
    }

    @Test
    fun `the browser rule should be applied if the parameters match`() {
        val rules = listOf(
            BrowserRule(browser = "Chrome", url = "https://chrome-only.com")
        )
        val engine = RuleEngine(rules)

        val context =
            RequestContext(device = DeviceType.DESKTOP, timeHour = 12, country = "RU", userAgent = "Chrome/122.0")
        val result = engine.process(context)

        assertEquals("https://chrome-only.com", result.redirectUrl)
    }

    @Test
    fun `The Country rule should be applied only to Tajikistan or Russia`() {
        val rules = listOf(
            CountryRule(countries = listOf("RU", "TJ"), url = "https://olddushanbe.mid.ru/o-rossijsko-tadzikskih-otnoseniah")
        )

        val engine = RuleEngine(rules)

        val context =
            RequestContext(device = DeviceType.DESKTOP, timeHour = 12, country = "TJ")

        val result = engine.process(context)

        assertEquals("https://olddushanbe.mid.ru/o-rossijsko-tadzikskih-otnoseniah", result.redirectUrl)
    }

    @Test
    fun `DeviceRule should not match other devices`() {
        val rule = DeviceRule(DeviceType.IOS, "https://apple.com")
        val context = RequestContext(DeviceType.ANDROID, 10, "US")
        assertFalse(rule.matches(context))
    }

    @Test
    fun `CountryRule should not match other countries`() {
        val rule = CountryRule(listOf("US", "UK"), "https://site.com")
        val context = RequestContext(DeviceType.ANDROID, 10, "TJ")
        assertFalse(rule.matches(context))
    }

    @Test
    fun `RuleEngine should call middlewares`() {
        val middlewares = listOf(LoggingMiddleware(), AnalyticsMiddleware())
        val rules = listOf(DeviceRule(DeviceType.ANDROID, "https://play.com"))
        val engine = RuleEngine(rules, middleware = middlewares)

        val context = RequestContext(DeviceType.ANDROID, 10, "US")
        val result = engine.process(context)

        assertEquals("https://play.com", result.redirectUrl)
    }



}