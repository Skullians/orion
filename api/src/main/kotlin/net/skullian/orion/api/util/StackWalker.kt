package net.skullian.orion.api.util

import net.skullian.orion.api.OrionApi
import java.lang.StackWalker

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
object StackWalker {

    private val walker: StackWalker = StackWalker.getInstance(
        StackWalker.Option.RETAIN_CLASS_REFERENCE
    )

    fun resolve(skipInternal: Boolean = true, max: Int = 12): StackSummary {
        val frames = walker.walk { stream ->
            stream.filter {
                if (!skipInternal) return@filter true
                val name = it.className

                !name.startsWith("net.skullian.orion.") &&
                !name.startsWith("sun.") &&
                !name.startsWith("java.") &&
                !name.startsWith("jdk.")
            }
            .limit(max.toLong())
            .toList()
        }

        val frame = frames.firstOrNull()
        val plugin = frame?.let { OrionApi().resolveAddon(it.declaringClass) }

        return StackSummary(frame?.className, frame?.methodName, plugin, frames.map { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" },)
    }

    data class StackSummary(
        val callerClass: String?,
        val callerMethod: String?,
        val callerPlugin: String?,
        val frames: List<String>
    ) {
        override fun toString(): String =
            frames.joinToString("\n") { " at $it" }
    }
}
