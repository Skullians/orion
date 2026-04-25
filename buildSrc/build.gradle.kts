plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://repo.skullian.com/releases/")
}

dependencies {
    implementation(libs.plugin.shadow)
    implementation(libs.plugin.zenith)
    implementation(libs.plugin.blossom)
    implementation(libs.plugin.git.version)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
