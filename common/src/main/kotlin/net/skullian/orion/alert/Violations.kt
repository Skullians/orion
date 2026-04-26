package net.skullian.orion.alert

import net.skullian.orion.api.check.Check
import net.skullian.orion.api.event.Violation
import net.skullian.orion.api.event.ViolationEvent
import net.skullian.orion.api.user.OrionPlayer
import net.skullian.orion.api.util.StackWalker
import net.skullian.zenith.core.ZenithPlatform

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
object Violations {

    @JvmStatic
    fun flag(player: OrionPlayer?, check: Check, details: String = "") {
        val stack = StackWalker.resolve()
        val context = Violation(
            player = player,
            check = check,
            details = details,
            stack = stack
        )

        val event = ViolationEvent(context)
        ZenithPlatform.getInstance().eventBus.emit(event)
    }
}
