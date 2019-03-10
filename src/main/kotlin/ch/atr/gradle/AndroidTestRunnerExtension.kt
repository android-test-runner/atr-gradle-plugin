package ch.atr.gradle

open class AndroidTestRunnerExtension(
    var executable: String = "",
    var devices: Array<String> = emptyArray(),
    var output: String = "build/atr",
    var recordScreen: Boolean = true,
    var recordLogcat: Boolean = true,
    var recordJunit: Boolean = true,
    var disableAnimations: Boolean = true,
    var verbose: Boolean = false
)