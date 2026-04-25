package net.skullian.orion

import net.skullian.orion.agent.AgentInstaller
import net.skullian.orion.agent.AgentLoader
import net.skullian.orion.api.check.CheckRegistry
import net.skullian.orion.api.check.annotation.CheckInfo
import net.skullian.zenith.core.flavor.Flavor
import net.skullian.zenith.core.flavor.FlavorOptions
import net.skullian.zenith.core.logging.adapters.impl.JavaLogAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
class Orion : JavaPlugin() {

    private lateinit var flavor: Flavor

    override fun onLoad() {
        instance = this

        flavor = Flavor.create(
            this,
            FlavorOptions(JavaLogAdapter(logger), this.javaClass.packageName)
        )

        val agent = extractAgent()
        if (!AgentInstaller.install(agent)) {
            logger.severe("""
                Failed to install Orion's agent. Orion will not function properly without it.
            """.trimIndent())
            server.pluginManager.disablePlugin(this)
            return
        }

        flavor.inherit(OrionBinder())

        flavor.listen(CheckInfo::class.java) { _, instance ->
            if (instance is net.skullian.orion.api.check.Check) CheckRegistry.register(instance)
        }
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onJoin(event: PlayerJoinEvent) {
                event.player.sendMessage("test")
            }
        }, this)

        flavor.startup()
    }

    override fun onDisable() {
        flavor.close()
    }

    private fun extractAgent(): File {
        val target = File(dataFolder, "agent.jar")
        dataFolder.mkdirs()
        getResource("agent.jar")!!.use { input ->
            Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        return target
    }

    companion object {
        @JvmStatic
        lateinit var instance: Orion
            private set
    }
}
