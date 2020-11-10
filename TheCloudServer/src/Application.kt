package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import java.io.PrintWriter
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val theCloudController = TheCloudController()
    initDB()
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {

        get("/all") { call.respond(theCloudController.getAll()) }

        get("/queries/1") { call.respond(theCloudController.getAddressName()) }
        get("/queries/2") { call.respond(theCloudController.getPaymentCustomer()) }
        get("/queries/3") { call.respond(theCloudController.getTypeSign()) }
        get("/queries/4") { call.respond(theCloudController.getPostDate()) }
        get("/queries/5") { call.respond(theCloudController.getIOSDev()) }
        get("/queries/6") { call.respond(theCloudController.getStatusCustomer()) }
        get("/queries/7") { call.respond(theCloudController.getVsSize()) }
        get("/queries/8") { call.respond(theCloudController.getPart()) }
        get("/queries/9") { call.respond(theCloudController.getCustomerSign()) }
        get("/queries/10") { call.respond(theCloudController.getDateSign()) }
        get("/queries/11") { call.respond(theCloudController.getExecEmail()) }
        get("/queries/12") { call.respond(theCloudController.getEmployersName()) }

    }
}


fun initDB() {
    val props = Properties()
    props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
    props.setProperty("dataSource.user", "postgres")
    props.setProperty("dataSource.password", "a1640Z89")
    props.setProperty("dataSource.databaseName", "TheCloud")

    props["dataSource.logWriter"] = PrintWriter(System.out)

    val config = HikariConfig(props)
    config.schema = "theCloud"
    val ds = HikariDataSource(config)
    Database.connect(ds)
}

