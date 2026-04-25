import net.skullian.orion.BuildConstants
import org.gradle.api.Project

val Project.buildNumber: String
    get() = this.providers.environmentVariable("BUILD_NUMBER").orNull?.let { " (build $it)" } ?: ""

val Project.fullVersion: String
    get() {
        val info = BuildConstants.details(this)
        return ("${info.branchName}/${this.version}" + (info.version?.let { ".$it" }
            ?: "") + buildNumber)
    }
