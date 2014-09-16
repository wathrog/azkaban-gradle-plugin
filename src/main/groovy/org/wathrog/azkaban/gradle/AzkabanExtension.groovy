package org.wathrog.azkaban.gradle

import org.apache.tools.ant.Project;
import org.gradle.api.NamedDomainObjectContainer
import org.wathrog.azkaban.gradle.domain.Flow

class AzkabanExtension {
    final NamedDomainObjectContainer<Flow> flows
    def File destDir
    def File libDir
    def File scriptsDir = new File('src/main/scripts')
    def File archiveDir

    AzkabanExtension(flows) {
        this.flows = flows
    }

    def flows(Closure closure) {
        flows.configure(closure)
    }
}
