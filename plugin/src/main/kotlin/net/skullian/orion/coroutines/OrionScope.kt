package net.skullian.orion.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.skullian.orion.Orion
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.coroutines.CoroutineContext

/**
 * This is a class.
 *
 * @author Skullians
 * @since 26/04/2026
 */
object OrionScope : CoroutineDispatcher() {
    private val group = ThreadGroup("orion-workers")

    val executor: ScheduledExecutorService = object : ScheduledThreadPoolExecutor(
        4,
        Thread.ofPlatform()
            .group(group)
            .name("orion-worker-", 0)
            .daemon()
            .uncaughtExceptionHandler { t, e ->
                Orion.instance.logger.severe("[OrionScope] Uncaught on $t: ${e.message}")
                e.printStackTrace()
            }
            .factory()
    ) {
        override fun afterExecute(r: Runnable?, t: Throwable?) {
            if (t != null) {
                Orion.instance.logger.severe("[OrionScope] Task failed: ${t.message}")
                t.printStackTrace()
            }
        }
    }

    val scope: CoroutineScope = CoroutineScope(
        this + SupervisorJob() + CoroutineExceptionHandler { _, e ->
            Orion.instance.logger.severe("[OrionScope] Uncaught in coroutine: ${e.message}")
            e.printStackTrace()
        }
    )

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return !group.parentOf(Thread.currentThread().threadGroup)
    }

    override fun dispatch(context: CoroutineContext, block: kotlinx.coroutines.Runnable) {
        if (isDispatchNeeded(context)) executor.execute(block) else block.run()
    }

    operator fun invoke(block: suspend CoroutineScope.() -> Unit) =
        scope.launch(block = block)
}
