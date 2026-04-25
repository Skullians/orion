import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.skullian.zenith.model.ZenithModules
import org.gradle.accessors.dm.LibrariesForLibs
plugins {
    `java-library`

    id("net.kyori.blossom")
    id("com.gradleup.shadow")
    id("net.skullian.zenith")
}

val libs = the<LibrariesForLibs>()
version = libs.versions.version.get()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.isFork = true
        options.encoding = "UTF-8"
        options.release = 25
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    withType<ShadowJar>().configureEach {
        destinationDirectory.set(file("$rootDir/output"))
        archiveFileName.set("${rootProject.name}-${project.name}-$version.jar")

        mergeServiceFiles()
    }

    withType<JavaExec> {
        jvmArgs = listOf(
            "-Dfile.encoding=UTF-8",
            "-Dstdout.encoding=UTF-8"
        )
    }
}
