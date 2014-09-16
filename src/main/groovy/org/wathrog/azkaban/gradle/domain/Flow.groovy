package org.wathrog.azkaban.gradle.domain

import org.gradle.api.NamedDomainObjectContainer

class Flow {
    def String name
    def NamedDomainObjectContainer<Job> jobs

    Flow(String name) {
        this.name = name
    }

    def jobs(Closure closure) {
        jobs.configure(closure)
    }
}
