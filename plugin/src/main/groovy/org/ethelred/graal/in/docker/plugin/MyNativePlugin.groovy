package org.ethelred.graal.in.docker.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.Delete

class MyNativePlugin implements Plugin<Project> {
    void apply(Project project) {
        project.pluginManager.apply(JavaPlugin)
        def extension = project.extensions.create('graalDocker', MyNativeExtension, project.objects)
        def generateNativeConfig = project.tasks.register('generateNativeConfig', GenerateNativeConfigTask, extension)
        def nativeBuild = project.tasks.register('nativeImage', NativeBuildTask, extension)
        def deploy = project.tasks.register('deploy', DeployTask, extension)
        generateNativeConfig.get().dependsOn(project.tasks.getByName('classes'))
        nativeBuild.get().dependsOn(project.tasks.getByName('classes'))
        deploy.get().dependsOn nativeBuild.get()
        def cleanNative = project.tasks.register('cleanNative', Delete) {
            delete("$project.projectDir/reports")
        }
        project.tasks['clean'].dependsOn(cleanNative)
    }
}
