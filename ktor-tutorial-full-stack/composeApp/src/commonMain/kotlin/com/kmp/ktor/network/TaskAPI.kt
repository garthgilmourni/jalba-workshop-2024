package com.kmp.ktor.network

import com.kmp.ktor.model.Task

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TaskApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                coerceInputValues = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            host = "192.168.254.41"
            port = 8080
        }
    }

    suspend fun getAllTasks(): List<Task> {
        return httpClient.get("tasks").body()
    }

    suspend fun removeTask(task: Task) {
        httpClient.delete("tasks/${task.name}")
    }

    suspend fun updateTask(task: Task) {
        httpClient.post("tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
    }
}