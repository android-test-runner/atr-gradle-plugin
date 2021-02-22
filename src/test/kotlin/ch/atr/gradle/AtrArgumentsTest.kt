package ch.atr.gradle

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class AtrArgumentsTest {
    @Test
    fun createsAllAtrArguments() {
        val apkPath = "apkPath"
        val testApkPath = "testApkPath"
        val testsPath = "testsPath"
        val device = "device"
        val output = "output"
        val atrArguments = AtrArguments(
            apkPath = apkPath,
            testApkPath = testApkPath,
            testsPath = testsPath,
            extension = AndroidTestRunnerExtension(
                devices = arrayOf(device),
                output = output,
                recordScreen = true,
                recordLogcat = true,
                recordJUnit = true,
                disableAnimations = true,
                verbose = true
            )
        )

        val result = atrArguments.all

        assertThat(
            result, CoreMatchers.`is`(
                arrayOf(
                    "--apk", apkPath,
                    "--testapk", testApkPath,
                    "--testfile", testsPath,
                    "--device", device,
                    "--output", output,
                    "--recordscreen",
                    "--recordlogcat",
                    "--recordjunit",
                    "--disableanimations",
                    "--verbose"
                )
            )
        )
    }

    @Test
    fun multipleDevices() {
        val apkPath = "apkPath"
        val testApkPath = "testApkPath"
        val testsPath = "testsPath"
        val device1 = "device1"
        val device2 = "device2"
        val output = "output"
        val atrArguments = AtrArguments(
            apkPath = apkPath,
            testApkPath = testApkPath,
            testsPath = testsPath,
            extension = AndroidTestRunnerExtension(
                devices = arrayOf(device1, device2),
                output = output,
                recordScreen = false,
                recordLogcat = false,
                recordJUnit = false,
                disableAnimations = false,
                verbose = false
            )
        )

        val result = atrArguments.all

        assertThat(
            result, CoreMatchers.`is`(
                arrayOf(
                    "--apk", apkPath,
                    "--testapk", testApkPath,
                    "--testfile", testsPath,
                    "--device", device1,
                    "--device", device2,
                    "--output", output
                )
            )
        )
    }

    @Test
    fun noDevices() {
        val apkPath = "apkPath"
        val testApkPath = "testApkPath"
        val testsPath = "testsPath"
        val output = "output"
        val atrArguments = AtrArguments(
            apkPath = apkPath,
            testApkPath = testApkPath,
            testsPath = testsPath,
            extension = AndroidTestRunnerExtension(
                devices = emptyArray(),
                output = output,
                recordScreen = false,
                recordLogcat = false,
                recordJUnit = false,
                disableAnimations = false,
                verbose = false
            )
        )

        val result = atrArguments.all

        assertThat(
            result, CoreMatchers.`is`(
                arrayOf(
                    "--apk", apkPath,
                    "--testapk", testApkPath,
                    "--testfile", testsPath,
                    "--output", output
                )
            )
        )
    }
}