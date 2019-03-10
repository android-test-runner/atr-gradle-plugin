package ch.atr.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.TestVariant
import com.linkedin.dex.parser.DexParser
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import java.io.File

class AndroidTestRunnerPlugin : Plugin<Project> {

    lateinit var logger: Logger

    override fun apply(project: Project) {
        logger = project.logger

        val extension = project.extensions.create("atr", AndroidTestRunnerExtension::class.java)

        project.task("atr").doLast {
            val atr = Atr(extension.executable, AtrArguments.fromExtenstion(extension))
            atr.runTests()
        }

        testVariants(project)?.all { createTestVariantTask(project, it) }
    }

    private fun createTestVariantTask(project: Project, variant: TestVariant) {
        logger.info("Creating task for test variant '${variant.name}'.")
        project.tasks.create("atrCollect${variant.name.capitalize()}").apply {
            description = "Collects test names for '${variant.name}' variant from APK."
            group = "Verification"
            dependsOn(variant.assembleProvider.get())
            doLast {
                val testApk = variant.outputs.first().outputFile
                val tests = DexParser.findTestNames(testApk.absolutePath)
                logger.info("Collected ${tests.count()} test names.")
                outputFile(project, "allTests.txt").printWriter().use { out ->
                    tests.forEach { out.println(it) }
                }
            }
        }
    }

    private fun testVariants(project: Project): DomainObjectSet<TestVariant>? {
        return when {
            project.plugins.hasPlugin("com.android.application") -> project.extensions.findByType(AppExtension::class.java)?.testVariants
            else -> throw IllegalStateException("The ATR Gradle plugin can only be used in Android application projects.")
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