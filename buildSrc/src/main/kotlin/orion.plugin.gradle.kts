import net.kyori.blossom.BlossomExtension
import net.skullian.orion.BuildConstants
import org.gradle.accessors.dm.LibrariesForLibs
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("net.kyori.blossom")
}

val libs = project.the<LibrariesForLibs>()

val buildDetails by lazy {
    BuildConstants.details(project)
}

// yes cas, i yoinked this
// cry about it puta
extensions.configure(SourceSetContainer::class) {
    this.named("main") {
        extensions.configure(BlossomExtension::class) {
            kotlinSources {
                this.property("version", project.fullVersion)
                this.property(
                    "timestamp",
                    ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME)
                )
                this.property("branch", buildDetails.branchName)
                this.property("commit", buildDetails.gitHashFull)
                this.property("ci", project.buildNumber.isNotBlank().toString())
                trimNewlines.set(false)
            }
        }
    }
}
