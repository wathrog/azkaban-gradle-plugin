package org.wathrog.azkaban.gradle

import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

abstract class AzkabanAbstractSpecification extends Specification {

    def project = ProjectBuilder.builder().build()

    def setup() {
        project.apply plugin: 'azkaban'
    }
}
