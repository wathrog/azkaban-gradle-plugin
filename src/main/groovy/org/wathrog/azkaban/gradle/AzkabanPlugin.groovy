package org.wathrog.azkaban.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip;
import org.wathrog.azkaban.gradle.domain.Flow
import org.wathrog.azkaban.gradle.domain.Job
import org.wathrog.azkaban.gradle.tasks.AzkabanJobFilesGenerationTask

class AzkabanPlugin implements Plugin<Project>{

    @Override
    public void apply(Project project) {
        def flows = project.container(Flow) {
            def flowsExt = project.extensions.azkaban.flows.extensions.create("$it", Flow, "$it".toString())
            project.extensions.azkaban.flows."$it".jobs = project.container(Job)
            flowsExt
        }

        def ext = project.extensions.create('azkaban', AzkabanExtension, flows)
        ext.destDir = new File("${project.buildDir.absolutePath}/azkaban")
        ext.archiveDir = new File("${project.buildDir.absolutePath}/azkabandist")

        def createTask = addJobFilesCreationTaskToProject(project)
        def prepareTask = addPrepareTaskToProject(project)
        def distTask = createZipFile(project)

        createTask.dependsOn(prepareTask)
        distTask.dependsOn(createTask)
    }

    protected Task addPrepareTaskToProject(Project project) {
        project.task('prepareStructure', type: Copy, group: 'Azkaban') {
            description = "Prepare the destination structure copying libraries and scripts"
            def libPath = project.extensions.azkaban.libDir
            if (libPath != null) from(libPath) { into 'lib' }
            from(project.extensions.azkaban.scriptsDir) { into 'scripts' }
            into project.extensions.azkaban.destDir
        }
    }

    protected Task addJobFilesCreationTaskToProject(Project project) {
        project.task('createJobFiles', type: AzkabanJobFilesGenerationTask, group: 'Azkaban') {
            description = "Generate the job files in the for the flows definition"
            baseOutputDir = project.extensions.azkaban.destDir
        }
    }
    
    protected Task createZipFile(Project project) {
        project.task('createDistributionZip', type: Zip, group: 'Azkaban') {
            description = "Pack everything up and create the distribution zip to be uploaded to Azkaban"
            destinationDir = project.extensions.azkaban.archiveDir
            from(project.extensions.azkaban.destDir)
        }
    }
}
