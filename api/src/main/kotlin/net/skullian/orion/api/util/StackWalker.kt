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

    private val IGNORED = listOf(
        "net.bytebuddy.",
        "net.skullian.orion.",
        "sun.reflect.",
        "jdk.internal.reflect.",
        "java.lang.reflect.",
    )
    private val NMS = listOf(
        "net.minecraft.",
        "com.mojang.",
        "io.papermc.",
        "org.bukkit.craftbukkit.",
        "org.spigotmc.",
    )

    private val walker: StackWalker = StackWalker.getInstance(
        StackWalker.Option.RETAIN_CLASS_REFERENCE
    )

    fun resolve(max: Int = 12): StackSummary {
        val raw = walker.walk { stream ->
            stream
                .filter { frame -> IGNORED.none { frame.className.startsWith(it) } }
                .limit((max * 3).toLong())
                .toList()
        }
        val formatted = raw.take(max).map { frame ->
            val tag = if (NMS.any { frame.className.startsWith(it) }) " [NMS]" else ""
            "${frame.className}.${frame.methodName}(${frame.fileName ?: "?"}:${frame.lineNumber})$tag"
        }

        val callerFrame = raw.firstOrNull { f -> NMS.none { f.className.startsWith(it) } } ?: raw.firstOrNull()
        val plugin = callerFrame?.let { OrionApi.instance.resolveAddon(it.declaringClass) }
        return StackSummary(
            callerClass = callerFrame?.className,
            callerMethod = callerFrame?.methodName,
            callerPlugin = plugin,
            frames = formatted,
        )
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
