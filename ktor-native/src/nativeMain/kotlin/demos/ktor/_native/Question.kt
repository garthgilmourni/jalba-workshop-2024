package demos.ktor._native

import kotlinx.serialization.Serializable

@Serializable
class Question(var id: Long, var title: String)