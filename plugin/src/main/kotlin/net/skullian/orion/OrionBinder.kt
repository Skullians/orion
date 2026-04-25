package net.skullian.orion

import net.skullian.zenith.core.flavor.binder.FlavorBinderContainer
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
class OrionBinder : FlavorBinderContainer() {

    override fun populate() {
        bind(Orion.instance)
            .to(Orion::class.java)
            .to(JavaPlugin::class.java)
            .to(Plugin::class.java)
            .bind()

        bind(Orion.instance.logger)
            .to(Logger::class.java)
            .bind()
    }
}
