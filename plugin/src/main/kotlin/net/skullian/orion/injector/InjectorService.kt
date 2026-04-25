package net.skullian.orion.injector

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.matcher.ElementMatchers.none
import net.skullian.orion.Orion
import net.skullian.orion.agent.AgentLoader
import net.skullian.orion.api.check.CheckRegistry
import net.skullian.zenith.core.flavor.annotation.Configure
import net.skullian.zenith.core.flavor.annotation.Service
import net.skullian.zenith.core.flavor.annotation.inject.Inject
import java.util.logging.Logger

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
@Service
object InjectorService {

    @Configure
    private fun configure() {
        val instrumentation = AgentLoader.require()

        val checks = CheckRegistry.all
        if (checks.isEmpty()) {
            return Orion.instance.logger.severe("No checks were registered. Orion will be non functional")
        }

        var builder: AgentBuilder = AgentBuilder.Default()
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
            .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
            .ignore(none())

        for (check in checks) {
            builder = builder.type(check.matcher).transform(check.buildTransformer())
        }

        builder.installOn(instrumentation)
    }

}
