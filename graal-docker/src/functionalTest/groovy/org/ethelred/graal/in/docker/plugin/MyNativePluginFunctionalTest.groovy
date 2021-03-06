/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package org.ethelred.graal.in.docker.plugin

import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'org.ethelred.native.image.in.docker.plugin.greeting' plugin.
 */
class MyNativePluginFunctionalTest extends Specification {
    def "can run nativeImage task"() {
        given:
        def projectDir = new File("build/functionalTest")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = """
            plugins {
                id('org.ethelred.graal.in.docker.plugin')
            }
            
            graalDocker {
                mainClassName = "testpackage.Hello"
            }
        """
        def srcDir = new File(projectDir, "src/main/java/testpackage")
        srcDir.mkdirs()
        new File(srcDir, "Hello.java").text = """
package testpackage;

public class Hello {
public static void main(String[] args) {
System.out.println("Hello World!");
}
}
"""

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("nativeImage")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("BUILD SUCCESSFUL")
    }

    def "can run generateNativeConfig task"() {
        given:
        def projectDir = new File("build/functionalTest")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = """
            plugins {
                id('org.ethelred.graal.in.docker.plugin')
            }
            
            graalDocker {
                mainClassName = "testpackage.Hello"
            }
        """
        def srcDir = new File(projectDir, "src/main/java/testpackage")
        srcDir.mkdirs()
        new File(srcDir, "Hello.java").text = """
package testpackage;

public class Hello {
public static void main(String[] args) {
System.out.println("Hello World!");
}
}
"""

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("generateNativeConfig")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("Hello World!")
        new File(projectDir, "src/main/resources/META-INF/native-image/reflect-config.json").exists()
    }
}
