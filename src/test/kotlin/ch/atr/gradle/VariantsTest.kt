package ch.atr.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class VariantsTest {

    @Test
    fun returningVariantsSucceeds() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.android.application")

        Variants(project).variants()
    }

    @Test(expected = IllegalStateException::class)
    fun returningVariantsThrowsException() {
        val project = ProjectBuilder.builder().build()

        Variants(project).variants()
    }
}