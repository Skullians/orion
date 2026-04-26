import net.skullian.zenith.model.ZenithModules
import net.skullian.zenith.paper

plugins {
    orion.common
    orion.kotlin
}

zenith {
    modules(ZenithModules.CORE, ZenithModules.PAPER)
    kotlin = true
}

dependencies {
    api(project(":common"))

    paper(libs.versions.paper.get())
    implementation(libs.bundles.bytebuddy)

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(libs.bundles.kotlin.ext)
}

plugin {
    main = "net.skullian.orion.Orion"
    authors = listOf("Skullians")
    description = "Who said tracking down exploits couldn't be fun?"
    foliaSupported = true
    apiVersion = "1.21"
    version = project.version.toString()
}
