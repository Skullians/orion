package net.skullian.orion.api.check

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.matcher.ElementMatcher
import net.skullian.orion.api.check.annotation.CheckInfo

/**
 * Represents an Orion detection check.
 *
 * It's a single target for instrumentation / redefinition (which class / method to hook, yada yada).
 * Orion will call [buildTransformer] on startup.
 *
 * ## Implement a check
 * ```kotlin
 * @Check(name = "SendMessage", description = "Tracks sendMessage calls (because why not)")
 * class SendMessageCheck : Check() {
 *     override val typeMatcher = nameContains("CraftPlayer")
 *     override val methodMatcher = named("sendMessage").and(takesArguments(String::class.java))
 *     override fun buildTransformer() = AgentBuilder.Transformer { builder, _, _, _, _ ->
 *         builder.visit(Advice.to(SendMessageAdvice::class.java).on(methodMatcher))
 *     }
 *
 *     object MessageAdvice {
 *         @JvmStatic
 *         @Advice.OnMethodEnter
 *         fun enter(@Advice.Argument(0) message: String?) {
 *             // logic here
 *         }
 *     }
 * }
 * ```
 *
 * > You **must** annotate all [Check]s with the [net.skullian.orion.api.check.annotation.CheckInfo] annotation.
 *
 * @author Skullians
 * @since 25/04/2026
 */
interface Check {
    /** Matches a class to instrument for this check */
    val matcher: ElementMatcher<TypeDescription>

    /**
     * Builds a [AgentBuilder.Transformer] that injects into the target method.
     */
    fun buildTransformer(): AgentBuilder.Transformer

    val info: CheckInfo
        get() = this::class.java.getAnnotation(CheckInfo::class.java)
            ?: throw IllegalStateException("Check ${this::class.java.name} is not annotated with @Check")
}
