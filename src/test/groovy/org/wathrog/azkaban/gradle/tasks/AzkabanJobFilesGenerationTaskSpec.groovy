package org.wathrog.azkaban.gradle.tasks

import java.nio.file.Files

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.wathrog.azkaban.gradle.AzkabanAbstractSpecification

class AzkabanJobFilesGenerationTaskSpec extends AzkabanAbstractSpecification {

    def "task added to project"() {
        setup:
        def project = ProjectBuilder.builder().build()
        project.task('createJobFiles2', type: AzkabanJobFilesGenerationTask)
        when:
        def tasks = project.getTasks()
        then:
        tasks.getByName('createJobFiles2') instanceof AzkabanJobFilesGenerationTask
    }

    def "outputs expected structure"() {
        when:
        project.azkaban {
            flows {
                aaaa {
                    jobs {
                        job1 { type = "noop" }
                        job2 {
                            type = "command"
                            command = "echo test"
                            deps = "job1"
                        }
                    }
                }
            }
        }

        def task = getTask(project) as AzkabanJobFilesGenerationTask
        def File workDir = Files.createTempDirectory("temp").toFile()
        task.baseOutputDir = workDir
        task.execute()

        then:
        workDir.list() == ['aaaa']
        workDir.listFiles()[0].list() == ['job1.job', 'job2.job']
        workDir.listFiles()[0].listFiles()[0].readLines().equals(['# job1.job', 'type=noop'])
        workDir.listFiles()[0].listFiles()[1].readLines().equals([
            '# job2.job',
            'type=command',
            'deps=job1',
            'command=echo test'
        ])

        cleanup:
        workDir.deleteDir()
    }

    private Task getTask(Project project) {
        project.getTasks().getByName('createJobFiles')
    }
}
