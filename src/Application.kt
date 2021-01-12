package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import java.io.PrintWriter
import java.text.DateFormat
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val query = TransactionController()
    dbInnit("2254")
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.SHORT)
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "TheCloudQuotaServer"
            validate {
                TokenKey(it.payload.getClaim("email").asString())
            }
        }
    }

   routing {
       post("/registration") {
           val accountData = call.receive<GraphQLRequest>()
           val emailKey = TokenKey(
               (query.tokenController.execute(accountData.query!!)
                   .substringAfterLast(':').substring(1)).substringBefore('"')
           )
           val token = JwtConfig.generateToken(emailKey.email)
           call.respond(
               query.tokenController.execute(
                   "mutation{postToken(token : \"$token\", email :\"${emailKey.email}\") }"
               )
           )
       }
       post("/login"){
           val graphQLRequest = call.receive<GraphQLRequest>()
           call.respond(query.accountController.execute(graphQLRequest.query!!))
       }
       authenticate {
           post("/account"){
               val graphQLRequest = call.receive<GraphQLRequest>()
               call.respond(query.accountController.execute(graphQLRequest.query!!))
           }
       }
   }
}

fun dbInnit(password: String){
    val props = Properties()
    props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
    props.setProperty("dataSource.user", "postgres")
    props.setProperty("dataSource.password", password)
    props.setProperty("dataSource.databaseName", "TheCloud")
    props["dataSource.logWriter"] = PrintWriter(System.out)
    val config = HikariConfig(props)
    config.schema = "cloud"
    val ds = HikariDataSource(config)
    Database.connect(ds)
}

