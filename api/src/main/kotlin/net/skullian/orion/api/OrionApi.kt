package net.skullian.orion.api

import net.skullian.orion.api.user.OrionPlayer

/**
 * This is a interface.
 *
 * @author Skullians
 * @since 26/04/2026
 */
interface OrionApi {

    /**
     * Resolves the platform specific addon (plugin / mod) from the given class.
     * May be null.
     */
    fun resolveAddon(clazz: Class<*>): String?

    /**
     * Adapts a platform-specific player handle to an [OrionPlayer].
     */
    fun adapt(handle: Any): OrionPlayer?

    /**
     * Adapts an [OrionPlayer] to a platform-specific player handle.
     */
    fun adapt(player: OrionPlayer): Any?

    companion object {
        @JvmStatic
        lateinit var instance: OrionApi

        operator fun invoke() = instance
    }
}
