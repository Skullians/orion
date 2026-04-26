package net.skullian.orion.config

import de.exlll.configlib.Comment
import de.exlll.configlib.Configuration
import de.exlll.configlib.NameFormatters
import de.exlll.configlib.YamlConfigurationProperties
import de.exlll.configlib.YamlConfigurations
import net.skullian.orion.Reload
import net.skullian.orion.api.check.action.CheckAction
import net.skullian.zenith.core.flavor.annotation.Configure
import net.skullian.zenith.core.flavor.annotation.Service
import net.skullian.zenith.core.flavor.annotation.inject.Inject
import java.nio.file.Path

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
@Service
object OrionConfig {

    @Inject private lateinit var dataFolder: Path
    lateinit var current: Config
        private set

    private val PROPERTIES = YamlConfigurationProperties.newBuilder()
        .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
        .charset(Charsets.UTF_8)
        .build()

    @Reload
    @Configure
    private fun configure() {
        current = YamlConfigurations.update(dataFolder.resolve("config.yml"), Config::class.java, PROPERTIES)
    }

    @Configuration
    class Config {
        var serverId: String = "prod-survival-01"
        var alerts: AlertsConfig = AlertsConfig()
        var checks: Map<String, CheckConfig> = emptyMap()
    }

    @Configuration
    class AlertsConfig {
        @Comment("MiniMessage format. Placeholders: <player> <check> <flags> <details> <plugin> <caller> <uuid>")
        var format: String = "<dark_gray>[<red>Orion<dark_gray>] <gray><player> " +
            "<dark_gray>| <yellow><check> <dark_gray>(<aqua><flags> flags<dark_gray>) " +
            "<dark_gray>\u203a <white><details>\n" +
            "<dark_gray>  \u21b3 <gray><plugin> <dark_gray>(<gray><caller><dark_gray>)"
        var showStack: Boolean = true
        var stackDepth: Int = 6
        var permission: String = "orion.alerts"
    }

    @Configuration
    class CheckConfig {
        var enabled: Boolean = true
        var experimental: Boolean = false
        var actions: List<ActionConfig> = emptyList()
    }

    @Configuration
    class ActionConfig {
        var type: CheckAction.CheckType = CheckAction.CheckType.KICK
        var threshold: Int = 0
        var reason: String = ""
        var command: String = ""
        var url: String = ""

        fun toAction(): CheckAction? = when (type) {
            CheckAction.CheckType.KICK -> CheckAction.Kick(reason.ifBlank { "<red>Flagged by Orion</red> <grey>(<white><check></white>)</grey>" }, threshold)
            CheckAction.CheckType.BAN -> CheckAction.Ban(reason.ifBlank { "<red>Banned by Orion</red> <grey>(<white><check></white>)</grey>" }, threshold)
            CheckAction.CheckType.COMMAND -> CheckAction.Command(command.ifBlank { return null }, threshold)
            CheckAction.CheckType.DISCORD -> CheckAction.Discord(url.ifBlank { return null }, threshold)
        }
    }
}
