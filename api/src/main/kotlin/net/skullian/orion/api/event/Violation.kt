package net.skullian.orion.api.event

import net.skullian.orion.api.check.Check
import java.util.UUID

/**
 * Represents a violation of a check by a player.
 *
 * @param player The UUID of the player who violated the check. This isn't always guaranteed to be a malicious player, mind you.
 * @param check The check that was violated.
 * @param details Any additional details about the violation.
 *
 * @author Skullians
 * @since 25/04/2026
 */
data class Violation(
    val player: UUID,
    val check: Check,
    val details: String = "",
)
