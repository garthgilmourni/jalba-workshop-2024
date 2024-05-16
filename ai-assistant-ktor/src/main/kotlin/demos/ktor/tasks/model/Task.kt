package demos.ktor.tasks.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val name: String,
    val description: String,
    val priority: Priority
)
