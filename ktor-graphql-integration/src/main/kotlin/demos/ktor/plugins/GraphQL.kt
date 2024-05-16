package demos.ktor.plugins

import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import demos.ktor.queries.HelloGraphQLQuery
import demos.ktor.queries.StackOverflowQuery
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGraphQL() {
    install(GraphQL) {
        schema {
            packages = listOf("demos.ktor.model")
            queries = listOf(
                HelloGraphQLQuery(),
                StackOverflowQuery()
            )
        }
    }
    install(Routing) {
        graphQLPostRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}