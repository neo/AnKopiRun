package pivotal.io.ankopirun.models

import com.fasterxml.jackson.annotation.JsonIgnore
import pivotal.io.ankopirun.RUN_TYPE
import pivotal.io.ankopirun.widgets.runlist.RunListAdapter
import java.io.Serializable

data class Run(val runnerName: String = "",
               val description: String = "",
               val duration: Long = 300,
               val createdAt: Long = 0L,
               @JsonIgnore var id: String = "") : Serializable {

    fun isInactive(currentTime: Long, skew: Long): Boolean {
        if (createdAt == 0L) return false
        return (createdAt + (duration * 1000) - currentTime) < 0
    }
}

