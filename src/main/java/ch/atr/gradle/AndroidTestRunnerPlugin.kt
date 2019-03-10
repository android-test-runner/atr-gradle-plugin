package ch.atr.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTestRunnerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("atr").doLast {
            println("Hello atr")
        }
    }

}