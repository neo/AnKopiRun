package pivotal.io.ankopirun.models

data class Run(val name: String, val location: String, val duration: Long = 300, val startTime: Long = 0L)

