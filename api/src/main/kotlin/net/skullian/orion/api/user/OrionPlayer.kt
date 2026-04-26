package net.skullian.orion.api.user

import java.util.UUID

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
data class OrionPlayer(
    val uniqueId: UUID,
    val handle: Any
) {

    fun flags(check: String): Int =
        TODO()

    fun isFlagged(check: String): Boolean = flags(check) > 0
}
