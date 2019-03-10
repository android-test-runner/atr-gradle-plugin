package ch.atr.gradle

import org.gradle.api.GradleException
import java.lang.ProcessBuilder.Redirect.PIPE
import java.util.concurrent.TimeUnit

class Atr(
    private val executable: String,
    private val atrArguments: AtrArguments
) {


    fun runTests() {


        val builder = ProcessBuilder(
            executable, "test",
            *atrArguments.all
        )
        val process = builder
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