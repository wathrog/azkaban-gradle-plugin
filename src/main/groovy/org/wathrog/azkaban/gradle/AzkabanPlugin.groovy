package org.wathrog.azkaban.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AzkabanPlugin implements Plugin<Project>{

    @Override
    public void apply(Project project) {

        def flows = project.container(Flow) {
            def flowsExt = project.extensions.azkaban.flows.extensions.create("$it", Flow, "$it".toString())
            project.extensions.azkaban.flows."$it".jobs = project.container(Job)
            flowsExt
        }

        project.extensions.create('azkaban', AzkabanExtension, flows)

    }

}
