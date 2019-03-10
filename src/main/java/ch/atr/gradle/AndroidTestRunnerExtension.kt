package ch.atr.gradle

open class AndroidTestRunnerExtension(
    var executable: String = "",
    var configuration: String = "atr.yml"
)