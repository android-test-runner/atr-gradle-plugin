package ch.atr.gradle

import java.io.File

class TestCollector(private val tests: List<String>, private val output: File) {

    fun collect() {
        output.printWriter().use { out ->
            tests.forEach { out.println(it) }
        }
    }
}