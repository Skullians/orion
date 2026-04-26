package net.skullian.orion.api.violation

import net.skullian.orion.api.check.Check
import net.skullian.orion.api.user.OrionPlayer

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
interface ViolationTracker {

    fun record(player: OrionPlayer, check: Check, timestamp: Long = System.currentTimeMillis())

    fun count(player: OrionPlayer, check: Check, since: Long = 0): Int

    fun clear(player: OrionPlayer)

    companion object {
        @JvmStatic
        lateinit var instance: ViolationTracker
    }
}
