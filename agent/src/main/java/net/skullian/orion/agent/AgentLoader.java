package net.skullian.orion.agent;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.instrument.Instrumentation;

/**
 * This is a class.
 *
 * @author Skullians
 * @since 25/04/2026
 */
public class AgentLoader {

    private static final String INSTALLER = "net.skullian.orion.agent.Agent";

    private AgentLoader() {}

    public static boolean install(final @NotNull File agent) {
        return get() != null; // todo - will handle bytebuddy later (probs in plugin)
    }

    @ApiStatus.Internal
    public static @Nullable Instrumentation get() {
        try {
            return (Instrumentation) Class.forName(INSTALLER, true, ClassLoader.getSystemClassLoader())
                .getMethod("get")
                .invoke(null);
        } catch (final Exception e) {
            return null;
        }
    }

    @ApiStatus.Internal
    public static @NotNull Instrumentation require() {
        final Instrumentation inst = get();
        if (inst == null) throw new IllegalStateException("Orion agent has not been installed.");
        return inst;
    }
}
