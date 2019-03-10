package ch.atr.gradle

import com.linkedin.dex.parser.DexParser
import java.io.File

class TestCollector(private val testApk: File, private val output: File) {

    fun collect() {
        val tests = DexParser.findTestNames(testApk.absolutePath)
        output.printWriter().use { out ->
            tests.forEach { out.println(it) }
        }
    }
}