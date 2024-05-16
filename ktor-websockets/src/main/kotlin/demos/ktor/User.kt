package demos.ktor

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: String) {
}