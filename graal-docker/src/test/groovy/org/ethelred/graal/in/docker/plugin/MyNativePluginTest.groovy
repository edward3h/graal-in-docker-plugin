/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package org.ethelred.graal.in.docker.plugin


import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import spock.lang.Specification

/**
 * A simple unit test for the 'org.ethelred.native.image.in.docker.plugin.greeting' plugin.
 */
class MyNativePluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("org.ethelred.graal.in.docker.plugin")

        then:
        project.tasks.findByName("nativeImage") != null
    }
}
