package org.ethelred.graal.in.docker.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject
import java.nio.file.Files

/**
 * TODO
 *
 * @author eharman* @since 2021-02-06
 */
class NativeBuildTask extends DefaultTask {
    @Input
    def extension

    @Inject
    NativeBuildTask(extension) {
        this.extension = extension
    }

    @TaskAction
    def perform() {
        _validateProperties()
        def thedir = project.rootProject.projectDir
        def ghome = project.gradle.gradleUserHomeDir
        def cp = project.sourceSets.main.runtimeClasspath
        project.exec {
            commandLine "docker", "run",
            "--volume", "${thedir}:${thedir}", "--workdir", project.projectDir,
            "--volume", "${ghome}:${ghome}:ro",
                    "--user", userString,
            // "--memory", "4g", "--oom-kill-disable",
            extension.dockerImageName.get(),
            "native-image", "--no-fallback",
            "--allow-incomplete-classpath",
            "--static",
            '--report-unsupported-elements-at-runtime',
            '--verbose',
            '-J-Xmx4G',"-R:MinHeapSize=2m", "-R:MaxHeapSize=10m", "-R:MaxNewSize=1m",
//            "-H:+PrintImageObjectTree",
            "-H:Log=registerResource",
            "-H:IncludeResources=.*logback.xml",
            "--initialize-at-build-time=org.ethelred",
                    "--initialize-at-build-time=ch.qos.logback,org.slf4j",
//                    "-H:+ReportExceptionStackTraces",
            "-cp", cp.asPath,
            extension.mainClassName.get(),
            "${project.buildDir}/deploy/${extension.appName.orElse(extension.mainClassName).get()}"
        }
    }

    @Input
    String getUserString() {
        if (extension.user.isPresent()) {
            return extension.user.get()
        }
        def dir = project.buildDir.toPath()
        def uid = Files.getAttribute(dir, "unix:uid")
        def gid = Files.getAttribute(dir, "unix:gid")
        "${uid}:${gid}".toString()
    }

    void _validateProperties() {
        if (!extension.mainClassName.isPresent())
        {
            throw new IllegalStateException("Missing value for property mainClassName")
        }
    }
}
