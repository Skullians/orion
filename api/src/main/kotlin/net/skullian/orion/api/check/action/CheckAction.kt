package net.skullian.orion.api.check.action

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
sealed interface CheckAction {

    val threshold: Int

    data class Kick(
        val reason: String = TODO(),
        override val threshold: Int = 0
    ) : CheckAction

    data class Ban(
        val reason: String = TODO(),
        override val threshold: Int = 0
    ) : CheckAction

    data class Command(
        val command: String,
        override val threshold: Int = 0
    ) : CheckAction

    data class Discord(
        val webhook: String,
        override val threshold: Int = 0
    ) : CheckAction

    enum class CheckType {
        KICK, BAN, COMMAND, DISCORD
    }
}
