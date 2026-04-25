package net.skullian.orion.api.check.annotation

/**
 * Marks a class as an Orion check.
 *
 * Any class annotated with [CheckInfo] that also implements [net.skullian.orion.api.check.Check]
 * will be registered on startup.
 *
 * @param name The name of the check - used for logging & cmds
 * @param description A description of the check (obviously)
 * @param experimental Whether this check is experimental (flagged in logging & cmds)
 *
 * @author Skullians
 * @since 25/04/2026
 */
annotation class CheckInfo(
    val name: String,
    val description: String = "",
    val experimental: Boolean = false
)
