package ch.atr.gradle

import com.android.build.gradle.api.ApkVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class AndroidTestRunnerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("atr", AndroidTestRunnerExtension::class.java)

        project.task("atr").doLast {
            val atr = Atr(extension.executable, AtrArguments.fromExtenstion(extension))
            atr.runTests()
        }

        Variants(project).variants()?.all { variant ->
            variant.testVariant?.let { testVariant ->
                createTestVariantTask(project, testVariant)
            }
        }
    }

    private fun createTestVariantTask(project: Project, variant: ApkVariant) {
        project.tasks.create("atrCollect${variant.name.capitalize()}").apply {
            description = "Collects test names for '${variant.name}' variant from APK."
            group = "Verification"
            dependsOn(variant.assembleProvider.get())
            doLast { TestCollector(variant.apk(), outputFile(project, "allTests.txt")).collect() }
        }
    }

    private fun outputFile(project: Project, name: String): File {
        val atrFolder = File(project.buildDir, "atr")
        if (!atrFolder.exists()) {
            atrFolder.mkdir()
        }
        val outputFile = File(atrFolder, name)
        outputFile.createNewFile()
        return outputFile
    }
}