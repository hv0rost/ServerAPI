package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.PrintWriter
import java.text.DateFormat
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val theCloudController = TheCloudController()
    initDB("a1640Z89")
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.SHORT)
            setPrettyPrinting()
        }
    }

    routing {
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

        route("/insert/city"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val cities = call.receive<Cities>()
                theCloudController.cityInsert(cities)
                call.respond(cities)
            }
        }

        route("/insert/account"){
            get("/") { call.respond(theCloudController.getAccount()) }
            post("/") {
                val account = call.receive<Accounts>()
                theCloudController.accountInsert(account)
                call.respond(account)
            }
        }

        route("/insert/address"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val address = call.receive<Addresses>()
                theCloudController.addressInsert(address)
                call.respond(address)
            }
        }
        route("/insert/customer"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val customer = call.receive<Customers>()
                theCloudController.customerInsert(customer)
                call.respond(customer)
            }
        }
        route("/insert/composContract"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val compositionContract = call.receive<CompositionContracts>()
                theCloudController.compositionContractInsert(compositionContract)
                call.respond(compositionContract)
            }
        }
        route("/insert/contract"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val contract = call.receive<Contracts>()
                theCloudController.contractInsert(contract)
                call.respond(contract)
            }
        }
        route("/insert/employer"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val employer = call.receive<Employers>()
                theCloudController.employerInsert(employer)
                call.respond(employer)
            }
        }
        route("/insert/execution"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val execution = call.receive<Executions>()
                theCloudController.executionInsert(execution)
                call.respond(execution)
            }
        }
        route("/insert/executionEmp"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val executionEmployer = call.receive<ExecutionEmployers>()
                theCloudController.executionEmployerInsert(executionEmployer)
                call.respond(executionEmployer)
            }
        }
        route("/insert/optionPrice"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val optionPrice = call.receive<OptionPrices>()
                theCloudController.optionPricesInsert(optionPrice)
                call.respond(optionPrice)
            }
        }
        route("/insert/participants"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val participants = call.receive<Participant>()
                theCloudController.participantsInsert(participants)
                call.respond(participants)
            }
        }
        route("/insert/payment"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val payment = call.receive<Payments>()
                theCloudController.paymentInsert(payment)
                call.respond(payment)
            }
        }
        route("/insert/position"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val position = call.receive<Positions>()
                theCloudController.positionInsert(position)
                call.respond(position)
            }
        }
        route("/insert/request"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val request = call.receive<Requests>()
                theCloudController.requestInsert(request)
                call.respond(request)
            }
        }
        route("/insert/signature"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val signature = call.receive<SignatureContracts>()
                theCloudController.signatureContractInsert(signature)
                call.respond(signature)
            }
        }
        route("/insert/vs"){
            get("/") { call.respond(theCloudController.getAll()) }
            post("/") {
                val virtServer = call.receive<VirtualServers>()
                theCloudController.virtualServerInsert(virtServer)
                call.respond(virtServer)
            }
        }
    }
}

fun initDB(password : String) {
    val props = Properties()
    props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
    props.setProperty("dataSource.user", "postgres")
    props.setProperty("dataSource.password", password)
    props.setProperty("dataSource.databaseName", "TheCloud")
    props["dataSource.logWriter"] = PrintWriter(System.out)

    val config = HikariConfig(props)
    config.schema = "theCloud"
    val ds = HikariDataSource(config)
    Database.connect(ds)
}

