package demos.ktor.queries

import com.expediagroup.graphql.server.operations.Query

class HelloGraphQLQuery : Query {
    fun hello(): String = "Hello GraphQL in Ktor!"
}