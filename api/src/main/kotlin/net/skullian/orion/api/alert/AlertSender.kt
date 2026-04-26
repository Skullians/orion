package net.skullian.orion.api.alert

import net.skullian.orion.api.event.Violation

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
interface AlertSender {

    fun dispatch(violation: Violation)

    companion object {
        @JvmStatic
        lateinit var instance: AlertSender
    }
}
