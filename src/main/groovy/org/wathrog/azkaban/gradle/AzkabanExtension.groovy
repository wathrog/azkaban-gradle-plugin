package org.wathrog.azkaban.gradle

import org.gradle.api.NamedDomainObjectContainer
import org.wathrog.azkaban.gradle.domain.Flow

class AzkabanExtension {
    final NamedDomainObjectContainer<Flow> flows

    AzkabanExtension(flows) {
        this.flows = flows
    }

    def flows(Closure closure) {
        flows.configure(closure)
    }
}
