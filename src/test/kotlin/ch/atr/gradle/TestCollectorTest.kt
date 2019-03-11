package ch.atr.gradle

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class TestCollectorTest {

    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Test
    fun writesTestsToFile() {
        val tests = listOf("ch.yvu.android_greeter.GreetingTest#canGreet")
        val output = temporaryFolder.newFile("tests.txt")

        TestCollector(tests, output).collect()

        assertThat(output.readText().trim(), `is`(tests.first()))
    }
}