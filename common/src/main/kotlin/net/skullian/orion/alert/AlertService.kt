package net.skullian.orion.alert

import net.skullian.orion.api.event.ViolationEvent
import net.skullian.zenith.core.ZenithPlatform
import net.skullian.zenith.core.event.EventPriority
import net.skullian.zenith.core.event.ZenithListener
import net.skullian.zenith.core.event.bus.Subscribe
import net.skullian.zenith.core.flavor.annotation.Configure
import net.skullian.zenith.core.flavor.annotation.Service

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
@Service
object AlertService : ZenithListener {

    @Configure
    private fun configure() {
        ZenithPlatform.getInstance().eventBus.subscribe(this)
    }

    @Subscribe(priority = EventPriority.MONITOR)
    private fun onViolation(event: ViolationEvent) {
        val violation = event.violation
        val info = violation.check.info

        // todo
    }
}
