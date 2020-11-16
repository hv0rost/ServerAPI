package com.example

import io.ktor.http.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.Table
import java.util.*


object Address : Table() {
    val idAdr = integer("idAdr")
    val name = varchar("name", 50)
    val idCustomer = (integer("idCustomer") references Customer.idCust)
    val idCity = (integer("idCity") references City.idCity)
}

object City : Table() {
    val idCity = integer("idCity").autoIncrement()
    val name = varchar("name", 50)
    override val primaryKey = PrimaryKey(idCity, name = "theCloud.City_pkey")
}

object Composition_Contract : Table() {
    val idCompos = integer("idCompos")
    val idContract = (integer("idContract") references Contract.idContr)
    val idRequest = (integer("idRequest") references Request.idReq)
}

object Contract : Table() {
    val idContr = integer("idContr")
}

object Customer : Table() {
    val idCust = integer("idCust")
    val FIO = varchar("fio", 60)
    val email = varchar("email", 60)
    val phone = varchar("phone", 15)
    val idCustomer = (integer("idCustomer") references idCust)
    val idRequest = (integer("idRequest") references Request.idReq)
}

object Employer : Table() {
    val idEmp = integer("idEmp")
    val name = varchar("name", 50)
    val idPosition = (integer("idPosition") references Position.idPos)
}

object Execution : Table() {
    val idExec = integer("idExec")
    val status = bool("status")
    val execDate = datetime("execDate")
    val idComposition = (integer("idComposition") references Composition_Contract.idCompos)
}

object Execution_Employer : Table() {
    val idExecEmp = integer("idExecEmp")
    val idEmp = (integer("idEmp") references Employer.idEmp)
    val idExecution = (integer("idExecution") references Execution.idExec)
}

object Option_Price : Table() {
    val idPrice = integer("idPrice")
    val price = float("price")
    val dateStart = datetime("dateStart")
    val dateEnd = datetime("dateEnd")
    val idOption = integer("idOption")
}

object Participants : Table() {
    val idPart = integer("idPart")
    val name = varchar("name", 40)
    val idContract = (integer("idContract") references Contract.idContr)
    val idCustomer = (integer("idCustomer") references Customer.idCust)
}

object Payment : IntIdTable() {
    val idPay = integer("idPay")
    val size = float("size")
    val payDate = datetime("payDate")
    val idContract = (integer("idContract") references Contract.idContr)
}

object Position : Table() {
    val idPos = integer("idPos")
    val post = varchar("post", 50)
}

object Request : Table() {
    val idReq = integer("idReq")
}

object Signature_Contract : Table() {
    val idSign = integer("idSign")
    val typeSign = varchar("typeSign", 40)
    val dateSign = datetime("dateSign")
    val idContract = (integer("idContract") references Contract.idContr)
    val idEmployer = (integer("idEmployer") references Employer.idEmp)
}

object Virtual_Server : Table() {
    val idVS = integer("idVS")
    val idRequest = (integer("idRequest") references Request.idReq)
}



