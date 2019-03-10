package ch.atr.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTestRunnerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("atr", AndroidTestRunnerExtension::class.java)

        project.task("atr").doLast {

            println("Configuration: atr ${extension.executable}, yanl ${extension.configuration}")

        }
    }

}