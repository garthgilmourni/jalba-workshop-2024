package demos.ktor.testing

import kotlinx.serialization.Serializable

@Serializable
class Question(var id: Long, var title: String)