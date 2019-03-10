package ch.atr.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTestRunnerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("atr", AndroidTestRunnerExtension::class.java)

        project.task("atr").doLast {
            val atr = Atr(extension.executable, extension.configuration)
            atr.runTests()
        }
    }

}