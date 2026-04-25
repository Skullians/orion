import net.skullian.zenith.model.ZenithModules

plugins {
    orion.common
    orion.kotlin
}

zenith {
    modules(ZenithModules.CORE)
    kotlin = true
}

dependencies {
    api(project(":agent"))

    compileOnlyApi(libs.bundles.bytebuddy)
}
