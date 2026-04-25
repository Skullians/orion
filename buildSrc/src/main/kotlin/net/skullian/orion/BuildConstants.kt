package net.skullian.orion

import com.palantir.gradle.gitversion.GitVersionCacheService
import com.palantir.gradle.gitversion.VersionDetails
import org.gradle.api.Project
import org.gradle.api.provider.Provider

object BuildConstants {
    fun details(project: Project): VersionDetails {
        val gitService: Provider<GitVersionCacheService> =
            GitVersionCacheService.getSharedGitVersionCacheService(project)
        return gitService.get().getVersionDetails(project.projectDir, null)
    }
}
