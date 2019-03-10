package ch.atr.gradle

import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test

internal class AtrArgumentsTest {
    @Test
    fun createsAllAtrArguments() {
        val apk = "apk"
        val testApk = "testApk"
        val device = "device"
        val test = "test"
        val output = "output"
        val atrArguments = AtrArguments(
            apk = apk,
            testApk = testApk,
            devices = arrayOf(device),
            tests = arrayOf(test),
            output = output,
            recordScreen = true,
            recordLogcat = true,
            recordJUnit = true,
            disableAnimations = true,
            verbose = true
        )

        val result = atrArguments.all

        assertThat(result, CoreMatchers.`is`(
            arrayOf(
               "--apk", apk,
                "--testapk", testApk,
                "--device", device,
                "--test", test,
                "--output", output,
                "--recordscreen",
                "--recordlogcat",
                "--recordjunit",
                "--disableanimations",
                "--verbose"
            )))
    }

    @Test
    fun multipleDevices() {
        val apk = "apk"
        val testApk = "testApk"
        val device1 = "device1"
        val device2 = "device2"
        val test = "test"
        val output = "output"
        val atrArguments = AtrArguments(
            apk = apk,
            testApk = testApk,
            devices = arrayOf(device1, device2),
            tests = arrayOf(test),
            output = output,
            recordScreen = false,
            recordLogcat = false,
            recordJUnit = false,
            disableAnimations = false,
            verbose = false
        )

        val result = atrArguments.all

        assertThat(result, CoreMatchers.`is`(
            arrayOf(
                "--apk", apk,
                "--testapk", testApk,
                "--device", device1,
                "--device", device2,
                "--test", test,
                "--output", output
            )))
    }

    @Test
    fun noDevices() {
        val apk = "apk"
        val testApk = "testApk"
        val test = "test"
        val output = "output"
        val atrArguments = AtrArguments(
            apk = apk,
            testApk = testApk,
            devices = emptyArray(),
            tests = arrayOf(test),
            output = output,
            recordScreen = false,
            recordLogcat = false,
            recordJUnit = false,
            disableAnimations = false,
            verbose = false
        )

        val result = atrArguments.all

        assertThat(result, CoreMatchers.`is`(
            arrayOf(
                "--apk", apk,
                "--testapk", testApk,
                "--test", test,
                "--output", output
            )))
    }

    @Test
    fun multipleTests() {
        val apk = "apk"
        val testApk = "testApk"
        val device = "device"
        val test1 = "test1"
        val test2 = "test2"
        val output = "output"
        val atrArguments = AtrArguments(
            apk = apk,
            testApk = testApk,
            devices = arrayOf(device),
            tests = arrayOf(test1, test2),
            output = output,
            recordScreen = false,
            recordLogcat = false,
            recordJUnit = false,
            disableAnimations = false,
            verbose = false
        )

        val result = atrArguments.all

        assertThat(result, CoreMatchers.`is`(
            arrayOf(
                "--apk", apk,
                "--testapk", testApk,
                "--device", device,
                "--test", test1,
                "--test", test2,
                "--output", output
            )))
    }

    @Test
    fun noTests() {
        val apk = "apk"
        val testApk = "testApk"
        val device = "device"
        val output = "output"
        val atrArguments = AtrArguments(
            apk = apk,
            testApk = testApk,
            devices = arrayOf(device),
            tests = emptyArray(),
            output = output,
            recordScreen = false,
            recordLogcat = false,
            recordJUnit = false,
            disableAnimations = false,
            verbose = false
        )

        val result = atrArguments.all

        assertThat(result, CoreMatchers.`is`(
            arrayOf(
                "--apk", apk,
                "--testapk", testApk,
                "--device", device,
                "--output", output
            )))
    }
}