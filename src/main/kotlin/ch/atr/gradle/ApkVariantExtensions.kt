package ch.atr.gradle

import com.android.build.gradle.api.ApkVariant
import java.io.File

fun ApkVariant.apk(): File =
    outputs.first().outputFile