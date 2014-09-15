package org.wathrog.azkaban.gradle

import org.wathrog.azkaban.gradle.domain.Flow
import org.wathrog.azkaban.gradle.domain.Job

class AzkabanPluginSpec extends AzkabanAbstractSpecification {

    def "correct plugin type instance"() {
        when:
        project.hashCode()
        then:
        project.extensions.findByName('azkaban') != null
    }

    def "dsl extension works"() {
        when:

        project.azkaban {
            flows {
                aaaa {
                    test = false
                    jobs {
                        bbbb {  type = "noop"  }
                    }
                }
            }
        }

        then:
        project.azkaban.flows.aaaa instanceof Flow
        project.azkaban.flows.aaaa.test == false
        project.azkaban.flows.aaaa.jobs.bbbb instanceof Job
        project.azkaban.flows.aaaa.jobs.bbbb.type == "noop"
    }
}
