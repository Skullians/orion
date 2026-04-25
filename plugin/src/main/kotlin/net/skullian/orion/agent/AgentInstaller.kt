package net.skullian.orion.agent

import net.bytebuddy.agent.ByteBuddyAgent
import java.io.File

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
internal object AgentInstaller {

    fun install(agentJar: File): Boolean {
        if (AgentLoader.get() != null) return true
        return try {
            ByteBuddyAgent.attach(agentJar, ByteBuddyAgent.ProcessProvider.ForCurrentVm.INSTANCE)
            AgentLoader.get() != null
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }
}
