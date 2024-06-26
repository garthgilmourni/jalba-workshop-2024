package demos.ktor.koin.plugins

import demos.ktor.koin.StackExchangeClient
import io.ktor.client.engine.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/fetch-questions/{user_id}") {
            val userID = call.parameters["user_id"]
            if (userID == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing User ID")
                return@get
            }
            try {
                val client = StackExchangeClient()
                val questions = client.fetchQuestions(userID.toLong())

                call.respond(questions)
            } catch (ex: NumberFormatException) {
                val message = "ID must be a number, received '$userID'"
                call.respond(HttpStatusCode.BadRequest, message)
            }
        }
    }
}
