package ch.atr.gradle

import com.android.build.gradle.api.ApkVariant
import com.linkedin.dex.parser.DexParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File

class AndroidTestRunnerPlugin : Plugin<Project> {

    companion object {
        private const val outputPath = "outputs/atr"
        private const val testsFileName = "tests.txt"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create("atr", AndroidTestRunnerExtension::class.java)

        Variants(project).variants()?.all { appVariant ->
            appVariant.testVariant?.let { testVariant ->
                val collectTask = createAtrCollectTask(project, testVariant)
                createAtrTask(project, extension, appVariant, testVariant, collectTask)
            }
        }
    }

    private fun createAtrCollectTask(project: Project, variant: ApkVariant): Task =
        project.tasks.create("atrCollect${variant.name.capitalize()}").apply {
            description = "Collects test names for '${variant.name}' variant from test APK."
            group = "Verification"
            dependsOn(variant.assembleProvider.get())
            doLast {
                val tests = DexParser.findTestNames(variant.apk().absolutePath)
                TestCollector(tests, outputFile(project, testsFileName)).collect()
            }
        }

    private fun createAtrTask(
        project: Project,
        extension: AndroidTestRunnerExtension,
        appVariant: ApkVariant,
        testVariant: ApkVariant,
        collectTask: Task
    ): Task =
        project.tasks.create("atr${testVariant.name.capitalize()}").apply {
            description = "Runs Android tests for '${appVariant.name}' variant."
            group = "Verification"
            dependsOn(appVariant.assembleProvider.get(), collectTask)
            doLast {
                val atrArguments = AtrArguments(
                    appVariant.apk().absolutePath,
                    testVariant.apk().absolutePath,
                    outputFile(project, testsFileName).absolutePath,
                    extension
                )
                Atr(extension.executable, atrArguments).runTests()
            }
        }

    private fun outputFile(project: Project, name: String): File {
        val atrFolder = File(project.buildDir, outputPath)
        if (!atrFolder.exists()) {
            atrFolder.mkdir()
        }
        val outputFile = File(atrFolder, name)
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
        return outputFile
    }
}