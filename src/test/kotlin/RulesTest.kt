import core.rules.BrowserRule
import core.rules.TimeRule
import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.RequestContext
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RulesTest {

    @Test()
    fun `should match when time in range`() {
        val rule = TimeRule(startHour = 22, endHour = 23, url = "https://night.com")
        val requestContext = RequestContext(device = DeviceType.ANDROID, timeHour = 22, country = "US")

        assertTrue(rule.matches(requestContext))
        val result = rule.execute(requestContext)
        assertEquals(expected = "https://night.com", actual = result.redirectUrl)
    }

    @Test
    fun `should not match when time outside range`() {
        val rule = TimeRule(startHour = 22, endHour = 23, url = "https://night.com")
        val requestContext = RequestContext(device = DeviceType.ANDROID, timeHour = 10, country = "US")
        assertFalse(actual = rule.matches(requestContext))
    }

    @Test
    fun `should match when time in wrapped range`() {
        val rule = TimeRule(startHour = 22, endHour = 6, url = "https://night.com")
        val context = RequestContext(DeviceType.ANDROID, timeHour = 1, country = "US")
        assertTrue(rule.matches(context))
    }

    @Test
    fun `BrowserRule should not match when userAgent is null`() {
        val rule = BrowserRule(browser = "Chrome", url = "https://chrome-only.com")
        val context = RequestContext(DeviceType.DESKTOP, 12, "RU", userAgent = null)
        assertFalse(rule.matches(context))
    }
}