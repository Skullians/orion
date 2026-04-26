pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.skullian.com/releases/")
    }
}

rootProject.name = "orion"

include(
    "api",
    "agent",
    "common",
    "plugin"
)
