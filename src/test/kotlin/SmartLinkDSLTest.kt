import core.dsl.smartLink
import core.rules.CountryRule
import core.rules.DeviceRule
import core.rules.TimeRule
import org.bot.core.rules.base.DeviceType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SmartLinkDSLTest {

    @Test
    fun `should build rules from DSL`() {
        val rules = smartLink {
            timeRule(22, 23, "https://night.com")
            deviceRule(DeviceType.ANDROID, "https://play.com")
        }

        assertEquals(2, rules.size)
        assertTrue(rules.any { it is TimeRule })
    }

    @Test
    fun `DSL should build mixed rules correctly`() {
        val rules = smartLink {
            deviceRule(DeviceType.IOS, "https://apple.com")
            countryRule("US", "UK", url = "https://example.com")
            timeRule(10, 12, "https://morning.com")
        }

        assertEquals(3, rules.size)
        assertTrue(rules.any { it is CountryRule })
        assertTrue(rules.any { it is DeviceRule })
    }


}