package org.wathrog.azkaban.gradle

import org.gradle.api.NamedDomainObjectContainer

class AzkabanExtension {
    final NamedDomainObjectContainer<Flow> flows
    Flow baseFlow
    
    AzkabanExtension(flows) {
        this.flows = flows
    }

    def flows(Closure closure) {
        flows.configure(closure)
    }
}

class Job {
    final String name
    def type
    def deps

    Job(String name) {
        this.name = name
    }
}

class Flow {
    def String name
    def Boolean test
    def NamedDomainObjectContainer<Job> jobs

    Flow(String name) {
        this.name = name
    }

    def jobs(Closure closure) {
        jobs.configure(closure)
    }
}
