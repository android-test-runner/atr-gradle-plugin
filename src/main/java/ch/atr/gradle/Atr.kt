package ch.atr.gradle

import org.gradle.api.GradleException
import java.lang.ProcessBuilder.Redirect.PIPE
import java.util.concurrent.TimeUnit

class Atr(executable: String, configurationFile: String) {

    val testProcessBuilder: ProcessBuilder = ProcessBuilder(executable, "test", "-l", configurationFile)

    fun runTests() {
        val process = testProcessBuilder
            .redirectError(PIPE)
            .redirectOutput(PIPE)
            .start()
        process.inputStream.copyTo(System.out)
        process.errorStream.copyTo(System.err)
        process.waitFor(30, TimeUnit.MINUTES)
        val exitValue = process.exitValue()
        if (exitValue != 0) {
            throw GradleException("ATR exited with value ${exitValue}")
        }
    }
}