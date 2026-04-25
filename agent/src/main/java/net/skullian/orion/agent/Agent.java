package net.skullian.orion.agent;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.instrument.Instrumentation;

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
public final class Agent {

    private static volatile Instrumentation instrumentation;

    private Agent() {}

    public static void premain(final @NotNull String args, final @NotNull Instrumentation instrumentation) {
        install(instrumentation);
    }

    public static void agentmain(final @NotNull String args, final @NotNull Instrumentation instrumentation) {
        install(instrumentation);
    }

    private static synchronized void install(final @NotNull Instrumentation instrumentation) {
        if (Agent.instrumentation != null) return;
        Agent.instrumentation = instrumentation;
    }

    @ApiStatus.Internal
    public static @NotNull Instrumentation get() {
        if (instrumentation == null) throw new IllegalStateException("Orion agent is not installed");
        return instrumentation;
    }
}
