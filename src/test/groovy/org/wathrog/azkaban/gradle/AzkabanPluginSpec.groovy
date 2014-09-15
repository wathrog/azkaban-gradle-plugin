package org.wathrog.azkaban.gradle

import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class AzkabanPluginSpec extends Specification {

    def project = ProjectBuilder.builder().build()

    def setup() {
        project.apply plugin: 'azkaban'
    }

    def "correct plugin type instance"() {
        when:
        project.hashCode()
        then:
        project.extensions.findByName('azkaban') != null
    }

    def "dsl works"() {
        when:

        project.azkaban {
            flows {
                aaaa {
                    test = false
                    jobs {
                        bbbb { type = "test" }
                    }
                }
            }
        }

        then:
        project.azkaban.flows.aaaa instanceof Flow
        project.azkaban.flows.aaaa.test == false
        project.azkaban.flows.aaaa.jobs.bbbb instanceof Job
        project.azkaban.flows.aaaa.jobs.bbbb.type == "test"
    }
}
