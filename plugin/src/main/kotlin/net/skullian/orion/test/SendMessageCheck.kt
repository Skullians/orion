package net.skullian.orion.test

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.method.MethodDescription
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.matcher.ElementMatcher
import net.bytebuddy.matcher.ElementMatchers.nameContains
import net.bytebuddy.matcher.ElementMatchers.named
import net.bytebuddy.matcher.ElementMatchers.takesArguments
import net.skullian.orion.api.check.Check
import net.skullian.orion.api.check.annotation.CheckInfo
import net.skullian.zenith.core.flavor.annotation.Service

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
@Service
@CheckInfo(
    name = "SendMessage",
    description = "test",
    experimental = true
)
object SendMessageCheck : Check {
    override val matcher: ElementMatcher<TypeDescription> =
        nameContains("CraftPlayer")

    override fun buildTransformer(): AgentBuilder.Transformer {
        return AgentBuilder.Transformer { builder, _, _, _, _ ->
            builder.visit(
                Advice.to(MessageAdvice::class.java)
                    .on(named<MethodDescription>("sendMessage").and(takesArguments(String::class.java)))
            ) as DynamicType.Builder<*>
        }
    }

    object MessageAdvice {
        @JvmStatic
        @Advice.OnMethodEnter
        fun enter(@Advice.Argument(0) message: String?) {
            println("ENTERED - $message")
            if (message?.contains("test", ignoreCase = true) == true) {
                println("T E S T")
            }
        }
    }
}
