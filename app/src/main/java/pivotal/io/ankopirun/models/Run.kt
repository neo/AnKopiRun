package pivotal.io.ankopirun.models

import com.fasterxml.jackson.annotation.JsonIgnore

data class Run(val name: String = "",
               val location: String = "",
               val duration: Long = 300,
               val startTime: Long = 0L,
               @JsonIgnore var id: String = "")