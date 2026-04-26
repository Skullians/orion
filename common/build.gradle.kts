import net.skullian.zenith.model.ZenithModules
import net.skullian.zenith.paper
import net.skullian.zenith.platform.paper.PaperPluginYml

plugins {
    orion.common
    orion.kotlin
}

zenith {
    modules(ZenithModules.CORE)
}

dependencies {
    api(project(":api"))

    api(libs.configlib)
}

tasks {
    processResources {
        from(project(":agent").tasks.named("shadowJar")) {
            rename { "agent.jar" }
            into(".")
        }
    }
}
