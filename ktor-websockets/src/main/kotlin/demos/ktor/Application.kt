package demos.ktor

import demos.ktor.plugins.configureRouting
import demos.ktor.plugins.configureSerialization
import demos.ktor.plugins.configureWebSockets
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(module {
            single { CIO.create() }
        })
    }

    configureRouting()
    configureSerialization()
    configureWebSockets()
}
