package org.bot.core.rules.base

data class RequestContext(

    val device: DeviceType,

    val timeHour: Int,

    val country: String,

    val userAgent: String? = null

)