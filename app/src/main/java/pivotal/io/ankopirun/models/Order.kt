package pivotal.io.ankopirun.models

data class Order(val description: String = "",
                 val requesterName: String = "",
                 val runUuid: String = "",
                 val createdAt: Long = 0L)