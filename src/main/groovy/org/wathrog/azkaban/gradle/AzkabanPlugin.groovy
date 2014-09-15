package org.wathrog.azkaban.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.wathrog.azkaban.gradle.domain.Flow
import org.wathrog.azkaban.gradle.domain.Job
import org.wathrog.azkaban.gradle.tasks.AzkabanJobFilesGenerationTask

class AzkabanPlugin implements Plugin<Project>{

    @Override
    public void apply(Project project) {
        project.task('createJobFiles', type: AzkabanJobFilesGenerationTask)

        def flows = project.container(Flow) {
            def flowsExt = project.extensions.azkaban.flows.extensions.create("$it", Flow, "$it".toString())
            project.extensions.azkaban.flows."$it".jobs = project.container(Job)
            flowsExt
        }

        project.extensions.create('azkaban', AzkabanExtension, flows)
    }
}
