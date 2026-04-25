plugins {
    orion.common
    orion.kotlin
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Premain-Class" to "net.skullian.orion.agent.Agent",
            "Agent-Class" to "net.skullian.orion.agent.Agent",
            "Can-Redefine-Classes" to "true",
            "Can-Retransform-Classes" to "true",
        )
    }
}
