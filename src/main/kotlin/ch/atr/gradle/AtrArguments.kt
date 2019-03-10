package ch.atr.gradle

class AtrArguments(
    private val apk: String,
    private val testApk: String,
    private val devices: Array<String>,
    private val tests: Array<String>,
    private val output: String,
    private val recordScreen: Boolean,
    private val recordLogcat: Boolean,
    private val recordJUnit: Boolean,
    private val disableAnimations: Boolean,
    private val verbose: Boolean
) {
    companion object {
        fun fromExtenstion(extension: AndroidTestRunnerExtension): AtrArguments {
            return AtrArguments(
                extension.apk,
                extension.testApk,
                extension.devices,
                arrayOf("GreetingTest#canGreet"),
                extension.output,
                extension.recordScreen,
                extension.recordLogcat,
                extension.recordJunit,
                extension.disableAnimations,
                extension.verbose
            )
        }
    }

    val all: Array<String>
        get() {
            val apkArgument = arrayOf("--apk", apk)
            val testApkArgument = arrayOf("--testapk", testApk)
            val deviceArguments = devices.flatMap { listOf("--device", it) }.toTypedArray()
            val testArguments = tests.flatMap { listOf("--test", it) }.toTypedArray()
            val outputArgument = arrayOf("--output", output)
            val recordScreenArgument = if (recordScreen) arrayOf("--recordscreen") else emptyArray()
            val recordLogcatArgument = if (recordLogcat) arrayOf("--recordlogcat") else emptyArray()
            val recordJUnitArgument = if (recordJUnit) arrayOf("--recordjunit") else emptyArray()
            val disableAnimationsArgument = if (disableAnimations) arrayOf("--disableanimations") else emptyArray()
            val verboseArgument = if (verbose) arrayOf("--verbose") else emptyArray()

            return apkArgument + testApkArgument + deviceArguments + testArguments + outputArgument + recordScreenArgument + recordLogcatArgument + recordJUnitArgument + disableAnimationsArgument + verboseArgument
        }
}