package core.dsl

import core.rules.BrowserRule
import core.rules.CountryRule
import core.rules.DeviceRule
import core.rules.TimeRule
import org.bot.core.rules.base.DeviceType
import org.bot.core.rules.base.Rule
//Interpreter and Builder pattern
class SmartLinkBuilder {

    private val rules = mutableListOf<Rule>()

    fun timeRule(start: Int, end: Int, url: String) {
        rules += TimeRule(start, end, url)
    }

    fun deviceRule(device: DeviceType, url: String) {
        rules += DeviceRule(device, url)
    }

    fun countryRule(vararg countries: String, url: String) {
        rules += CountryRule(countries.toList(), url)
    }

    fun browserRule(browser: String, url: String) {
        rules += BrowserRule(browser, url)
    }


    fun build(): List<Rule> = rules

}

fun smartLink(block: SmartLinkBuilder.() -> Unit): List<Rule> =
    SmartLinkBuilder().apply(block).build()
