package ch.atr.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project

class Variants(private val project: Project) {

    fun variants(): DomainObjectSet<ApplicationVariant>? {
        return when {
            project.plugins.hasPlugin("com.android.application") -> project.extensions.findByType(AppExtension::class.java)?.applicationVariants
            else -> throw IllegalStateException("The ATR Gradle plugin can only be used in Android application projects.")
        }
    }
}