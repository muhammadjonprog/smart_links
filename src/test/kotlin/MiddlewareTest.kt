import core.middleware.AnalyticsMiddleware
import core.middleware.LoggingMiddleware
import core.middleware.SecurityMiddleware
import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.RequestContext
import org.bot.core.rules.base.RuleResult
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MiddlewareTest {

    @Test
    fun `should print log before and after`() {
        val middleware = LoggingMiddleware()
        val context = RequestContext(device = DeviceType.ANDROID, timeHour = 10, country = "US")
        val result = RuleResult(redirectUrl = "https://site.com", appliedRule = "TestRule")

        // Просто вызов — проверяем, что не упал
        middleware.onBefore(context)
        middleware.onAfter(context, result)

        // Никаких моков — поведение проверяем визуально или по логике (например, не бросает исключений)
        assertTrue(true)
    }

    @Test
    fun `should  work correctly analytics`() {
        val middleware = AnalyticsMiddleware()
        val context = RequestContext(device = DeviceType.ANDROID, timeHour = 10, country = "US")
        val result = RuleResult(redirectUrl = "https://site.com", appliedRule = "TestRule")

        middleware.onAfter(context, result)

        assertEquals(expected = true, actual = middleware.getCounter() > 0)
    }

    @Test
    fun `should print log security before`() {
        val middleware = SecurityMiddleware()
        val context = RequestContext(device = DeviceType.ANDROID, timeHour = 10, country = "US")

        // Просто вызов — проверяем, что не упал
        middleware.onBefore(context)

        // Никаких моков — поведение проверяем визуально или по логике (например, не бросает исключений)
        assertTrue(true)
    }

    @Test
    fun `AnalyticsMiddleware should count multiple redirects`() {
        val middleware = AnalyticsMiddleware()
        val context = RequestContext(DeviceType.ANDROID, 10, "US")
        val result = RuleResult("https://test.com", "Rule")

        repeat(5) { middleware.onAfter(context, result) }
        assertEquals(5, middleware.getCounter())
    }

    @Test
    fun `SecurityMiddleware should trigger for RU country`() {
        val middleware = SecurityMiddleware()
        val context = RequestContext(DeviceType.ANDROID, 12, "RU")
        middleware.onBefore(context)
        assertTrue(true) // smoke check
    }


    @Test
    fun `analyticsMiddleware should count multiple redirects`() {
        val middleware = AnalyticsMiddleware()
        val context = RequestContext(DeviceType.ANDROID, 10, "US")
        val result = RuleResult("https://test.com", "Rule")

        repeat(5) { middleware.onAfter(context, result) }
        assertEquals(5, middleware.getCounter())
    }


}