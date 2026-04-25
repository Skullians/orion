package net.skullian.orion.api.check

/**
 * Main registry for all checks.
 *
 * Checks are registered on startup via classpath scanning.
 * You can also just rawdog the [register] calls if need be.
 *
 * @author Skullians
 * @since 25/04/2026
 */
object CheckRegistry {

    private val checks: MutableMap<String, Check> = LinkedHashMap()

    /**
     * Register a check to the registry.
     * Must be annotated with [net.skullian.orion.api.check.annotation.CheckInfo].
     *
     * @param check The [Check] to register.
     */
    fun register(check: Check) {
        checks += check.info.name to check
    }

    /**
     * Unregister a given check from the registry.
     *
     * @param check The [Check] to unregister.
     */
    fun unregister(check: Check) {
        checks -= check.info.name
    }

    /**
     * Get a check by name.
     *
     * @param name The name of the check to get.
     */
    fun get(name: String): Check? = checks[name]

    /**
     * Check if a check is registered by name.
     *
     * @param name The name of the check to check.
     */
    fun isRegistered(name: String): Boolean = checks.containsKey(name)

    /**
     * Get all registered checks.
     * Used by Orion internally for check execution but feel free to use it :p
     */
    val all: Collection<Check>
        get() = checks.values.toList()

    /**
     * Clears all registered checks.
     */
    internal fun clear() = checks.clear()
}
