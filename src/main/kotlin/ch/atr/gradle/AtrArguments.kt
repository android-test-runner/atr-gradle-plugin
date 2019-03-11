package ch.atr.gradle

class AtrArguments(
    private val apkPath: String,
    private val testApkPath: String,
    private val testsPath: String,
    private val extension: AndroidTestRunnerExtension
) {
    val all: Array<String>
        get() {
            val apkArgument = arrayOf("--apk", apkPath)
            val testApkArgument = arrayOf("--testapk", testApkPath)
            val testFileArgument = arrayOf("--testfile", testsPath)
            val deviceArguments = extension.devices.flatMap { listOf("--device", it) }.toTypedArray()
            val outputArgument = arrayOf("--output", extension.output)
            val recordScreenArgument = if (extension.recordScreen) arrayOf("--recordscreen") else emptyArray()
            val recordLogcatArgument = if (extension.recordLogcat) arrayOf("--recordlogcat") else emptyArray()
            val recordJUnitArgument = if (extension.recordJUnit) arrayOf("--recordjunit") else emptyArray()
            val disableAnimationsArgument = if (extension.disableAnimations) arrayOf("--disableanimations") else emptyArray()
            val verboseArgument = if (extension.verbose) arrayOf("--verbose") else emptyArray()

            return apkArgument + testApkArgument + testFileArgument + deviceArguments + outputArgument + recordScreenArgument + recordLogcatArgument + recordJUnitArgument + disableAnimationsArgument + verboseArgument
        }
}