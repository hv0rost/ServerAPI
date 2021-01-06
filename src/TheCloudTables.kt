package com.example

import com.example.City.autoIncrement
import com.example.Customer.references
import io.ktor.http.*
import io.netty.resolver.AddressResolver
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.Table
import java.util.*

object Account : Table() {
    val idAccount = integer("idAccount").autoIncrement()
    val login = varchar("login", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val phone = varchar("phone", 20)

    val token = varchar("token", 300)

    override val primaryKey = PrimaryKey(idAccount, name = "account_pkey")

    fun accountToMap(row: ResultRow): AccountData =
        AccountData(
            idAccount = row[idAccount],
            login = row[login],
            email = row[email],
            password = row[password],
            phone = row[phone],
            token = row[token]
        )
    fun getAuth(row: ResultRow): AccountData =
        AccountData(
            email = row[email],
            password = row[password]
        )
}

object Address : Table() {
    val idAdr = integer("idAdr").autoIncrement()
    val name = varchar("name", 50)
    val idCustomer = (integer("idCustomer") references Customer.idCust)
    val idCity = (integer("idCity") references City.idCity)

    override val primaryKey = PrimaryKey(idAdr, name ="Address_pkey")

    fun addressToMap(row: ResultRow): AddressData =
        AddressData(
            idAdr = row[idAdr],
            name = row[name],
            idCustomer = row[idCustomer],
            idCity = row[idCity],

        )
}

object City : Table() {
    val idCity = integer("idCity").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(idCity, name = "City_pkey")
}

object Composition_Contract : Table() {
    val idCompos = integer("idCompos").autoIncrement()
    val idContract = (integer("idContract") references Contract.idContr)
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idCompos, name = "CompositionContract_pkey")
}

object Contract : Table() {
    val idContr = integer("idContr")/*.autoIncrement()*/
    override val primaryKey = PrimaryKey(idContr, name = "Contract_pkey")
}

object Customer : Table() {
    val idCust = integer("idCust")/*.autoIncrement()*/
    val FIO = varchar("fio", 60)
    val email = varchar("email", 60)
    val phone = varchar("phone", 15)
    val idCustomer = (integer("idCustomer") references idCust)
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idCust, name = "Customer_pkey")
}

object Employer : Table() {
    val idEmp = integer("idEmp").autoIncrement()
    val name = varchar("name", 50)
    val idPosition = (integer("idPosition") references Position.idPos)

    override val primaryKey = PrimaryKey(idEmp, name = "Employer_pkey")
}

object Execution : Table() {
    val idExec = integer("idExec").autoIncrement()
    val status = bool("status")
    val execDate = datetime("execDate")
    val idComposition = (integer("idComposition") references Composition_Contract.idCompos)

    override val primaryKey = PrimaryKey(idExec, name = "Execution_pkey")
}

object Execution_Employer : Table() {
    val idExecEmp = integer("idExecEmp").autoIncrement()
    val idEmp = (integer("idEmp") references Employer.idEmp)
    val idExecution = (integer("idExecution") references Execution.idExec)

    override val primaryKey = PrimaryKey(idExecEmp, name = "ExecutionEmployer_pkey")
}

object Option_Price : Table() {
    val idPrice = integer("idPrice").autoIncrement()
    val price = float("price")
    val dateStart = datetime("dateStart")
    val dateEnd = datetime("dateEnd")
    val idOption = integer("idOption")

    override val primaryKey = PrimaryKey(idPrice, name = "OptionPrice_pkey")
}

object Participants : Table() {
    val idPart = integer("idPart").autoIncrement()
    val name = varchar("name", 40)
    val idContract = (integer("idContract") references Contract.idContr)
    val idCustomer = (integer("idCustomer") references Customer.idCust)

    override val primaryKey = PrimaryKey(idPart, name = "Participants_pkey")
}

object Payment : IntIdTable() {
    val idPay = integer("idPay").autoIncrement()
    val size = float("size")
    val payDate = datetime("payDate")
    val idContract = (integer("idContract") references Contract.idContr)

    override val primaryKey = PrimaryKey(idPay, name = "Payment_pkey")

}

object Position : Table() {
    val idPos = integer("idPos").autoIncrement()
    val post = varchar("post", 50)

    override val primaryKey = PrimaryKey(idPos, name = "Position_pkey")

}

object Request : Table() {
    val idReq = integer("idReq").autoIncrement()
    override val primaryKey = PrimaryKey(idReq, name = "Request_pkey")
}

object Signature_Contract : Table() {
    val idSign = integer("idSign").autoIncrement()
    val typeSign = varchar("typeSign", 40)
    val dateSign = datetime("dateSign")
    val idContract = (integer("idContract") references Contract.idContr)
    val idEmployer = (integer("idEmployer") references Employer.idEmp)

    override val primaryKey = PrimaryKey(idSign, name = "SignatureContract_pkey")
}

object Virtual_Server : Table() {
    val idVS = integer("idVS").autoIncrement()
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idVS, name = "VirtualServer_pkey")
}



