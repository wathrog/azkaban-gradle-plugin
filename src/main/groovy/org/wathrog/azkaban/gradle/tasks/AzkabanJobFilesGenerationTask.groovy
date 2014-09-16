package org.wathrog.azkaban.gradle.tasks;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.wathrog.azkaban.gradle.domain.Flow
import org.wathrog.azkaban.gradle.domain.Job

public class AzkabanJobFilesGenerationTask extends DefaultTask {
    private final static String SEPARATOR = File.separator
    private final static String WORK_DIR = 'azkaban'

    def flows

    @OutputDirectory
    File baseOutputDir = new File(project.getBuildDir().getPath() + AzkabanJobFilesGenerationTask.SEPARATOR + WORK_DIR)

    @TaskAction
    def generateJobFiles() {
        if (flows == null) {
            flows = project.azkaban.flows
        }

        flows.each { Flow flow ->
            File outputDir = new File(baseOutputDir.getPath() + AzkabanJobFilesGenerationTask.SEPARATOR + flow.getName())
            outputDir.mkdirs()
            flow.jobs.each { Job job ->
                new File(outputDir.getPath() + AzkabanJobFilesGenerationTask.SEPARATOR + job.getName()+".job").withWriter { out ->
                    out.println("# "+job.getName()+".job")
                    out.println("type="+job.getType())
                    if (job.getDependencies() != null) out.println("dependencies="+job.getDependencies())
                    final String jobSpecProps = job.getJobTypeSpecificProperties()
                    if (jobSpecProps != null) out.println(job.getJobTypeSpecificProperties())
                }
            }
        }
    }
}
