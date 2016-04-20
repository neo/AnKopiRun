package pivotal.io.ankopirun.models

import com.fasterxml.jackson.annotation.JsonIgnore
import pivotal.io.ankopirun.RUN_TYPE
import pivotal.io.ankopirun.widgets.runlist.RunListAdapter
import java.io.Serializable

data class Run(val name: String = "",
               val location: String = "",
               val duration: Long = 300,
               val startTime: Long = 0L,
               @JsonIgnore var id: String = "") : Serializable {

    fun isInactive(currentTime: Long, skew: Long): Boolean {
        if (startTime == 0L) return false
        return (startTime + (duration * 1000) - currentTime) < 0
    }
}

