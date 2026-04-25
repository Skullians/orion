package net.skullian.orion.api.event

import net.skullian.zenith.core.event.Cancellable
import net.skullian.zenith.core.event.ZenithEvent

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
class ViolationEvent(
    val violation: Violation
) : ZenithEvent, Cancellable {
    private var _cancelled: Boolean = false

    override fun isCancelled(): Boolean = _cancelled

    override fun setCancelled(cancelled: Boolean) {
        _cancelled = cancelled
    }
}
