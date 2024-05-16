package com.example.plugins

import com.example.model.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.routing.get

fun Application.configureRouting() {
    routing {

        staticResources("/task-ui", "task-ui")

        get("/") {
            call.respondText("Hello World!")
        }
        get("/tasks") {
            val tasks = TaskRepository.allTasks()
            call.respondText(
                contentType = ContentType.parse("text/html"),
                text = tasks.tasksAsTable()
            )
        }
        get("/tasks/{taskName}") {
            val name = call.parameters["taskName"]
            if (name != null) {
                val task = TaskRepository.taskByName(name)
                if (task != null) {
                    call.respondText(
                        contentType = ContentType.parse("text/html"),
                        text = listOf(task).tasksAsTable()
                    )
                    return@get
                }
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post("/tasks") {
            val formContent = call.receiveParameters()
            val params = Triple(
                formContent["name"] ?: "",
                formContent["description"] ?: "",
                formContent["priority"] ?: ""
            )
            if (params.toList().any { it.isEmpty() }) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            } else {
                try {
                    val priority = Priority.valueOf(params.third)
                    TaskRepository.addTask(
                        Task(
                            params.first,
                            params.second,
                            priority
                        )
                    )
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
