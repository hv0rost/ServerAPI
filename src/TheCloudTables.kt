package com.example
import com.google.gson.Gson
import jsonb
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.Table


object Account : Table() {
    val idAccount = integer("idAccount").autoIncrement()
    val login = varchar("login", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val phone = varchar("phone", 20)
    val info = jsonb("info", Map::class.java, Gson(), true)

    val token = varchar("token", 300)

    override val primaryKey = PrimaryKey(idAccount, name = "account_pkey")

    fun accountToMap(row: ResultRow): AccountData =
        AccountData(
            idAccount = row[idAccount],
            login = row[login],
            email = row[email],
            password = row[password],
            phone = row[phone],
            token = row[token],
            info = row[info].toString()
        )

    fun getAuth(row: ResultRow): AccountData =
        AccountData(
            email = row[email]
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

    fun cityToMap(row: ResultRow): CityData =
        CityData(
            idCity = row[idCity],
            name = row[name]
        )
}

object Composition_Contract : Table() {
    val idCompos = integer("idCompos").autoIncrement()
    val idContract = (integer("idContract") references Contract.idContr)
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idCompos, name = "CompositionContract_pkey")

    fun  composContractToMap(row: ResultRow) : CompositionData =
        CompositionData(
            idCompos = row[idCompos],
            idContract = row[idContract],
            idRequest = row[idRequest]
        )
}

object Contract : Table() {
    val idContr = integer("idContr")/*.autoIncrement()*/
    override val primaryKey = PrimaryKey(idContr, name = "Contract_pkey")

    fun  contractToMap(row: ResultRow) : ContractData =
        ContractData(
            idContr = row[idContr]
        )
}

object Customer : Table() {
    val idCust = integer("idCust")/*.autoIncrement()*/
    val FIO = varchar("fio", 60)
    val idCustomer = (integer("idCustomer") references idCust)
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idCust, name = "Customer_pkey")

    fun  customerToMap(row: ResultRow) : CustomerData =
        CustomerData(
            idCust = row[idCust],
            FIO = row[FIO],
            idCustomer = row[idCustomer],
            idRequest = row[idRequest]
        )
}

object Employer : Table() {
    val idEmp = integer("idEmp").autoIncrement()
    val name = varchar("name", 50)
    val idPosition = (integer("idPosition") references Position.idPos)

    override val primaryKey = PrimaryKey(idEmp, name = "Employer_pkey")

    fun  employerToMap(row: ResultRow) : EmployerData =
        EmployerData(
            idEmp = row[idEmp],
            name = row[name],
            idPosition = row[idPosition]
        )
}

object Execution : Table() {
    val idExec = integer("idExec").autoIncrement()
    val status = bool("status")
    val execDate = datetime("execDate")
    val idComposition = (integer("idComposition") references Composition_Contract.idCompos)

    override val primaryKey = PrimaryKey(idExec, name = "Execution_pkey")

    fun  executionToMap(row: ResultRow) : ExecutionData =
        ExecutionData(
            idExec = row[idExec],
            status = row[status],
            execDate = row[execDate].toString("dd-MM-yyyy"),
            idComposition = row[idComposition]
        )
}

object Execution_Employer : Table() {
    val idExecEmp = integer("idExecEmp").autoIncrement()
    val idEmp = (integer("idEmp") references Employer.idEmp)
    val idExecution = (integer("idExecution") references Execution.idExec)

    override val primaryKey = PrimaryKey(idExecEmp, name = "ExecutionEmployer_pkey")

    fun  execEmployerToMap(row: ResultRow) : ExecEmployerData =
        ExecEmployerData(
            idExecEmp = row[idExecEmp],
            idEmp = row[idEmp],
            idExecution = row[idExecution]
        )
}

object Option_Price : Table() {
    val idPrice = integer("idPrice").autoIncrement()
    val price = float("price")
    val dateStart = datetime("dateStart")
    val dateEnd = datetime("dateEnd")
    val idOption = integer("idOption")

    override val primaryKey = PrimaryKey(idPrice, name = "OptionPrice_pkey")

    fun  priceToMap(row: ResultRow) : PriceData =
        PriceData(
            idPrice = row[idPrice],
            price = row[price],
            dateStart = row[dateStart].toString("dd-MM-yyyy"),
            dateEnd = row[dateEnd].toString("dd-MM-yyyy"),
            idOption = row[idOption]
        )
}

object Participants : Table() {
    val idPart = integer("idPart").autoIncrement()
    val name = varchar("name", 40)
    val idContract = (integer("idContract") references Contract.idContr)
    val idCustomer = (integer("idCustomer") references Customer.idCust)

    override val primaryKey = PrimaryKey(idPart, name = "Participants_pkey")

    fun participantsToMap(row: ResultRow) : ParticipantData =
        ParticipantData(
            idPart = row[idPart],
            name = row[name],
            idContract = row[idContract],
            idCustomer = row[idCustomer]
        )
}

object Payment : IntIdTable() {
    val idPay = integer("idPay").autoIncrement()
    val size = float("size")
    val payDate = datetime("payDate")
    val idContract = (integer("idContract") references Contract.idContr)

    override val primaryKey = PrimaryKey(idPay, name = "Payment_pkey")

    fun paymentToMap(row: ResultRow) : PaymentData =
        PaymentData(
            idPay = row[idPay],
            size = row[size],
            payDate = row[payDate].toString("dd-MM-yyyy"),
            idContract = row[idContract]
        )
}

object Position : Table() {
    val idPos = integer("idPos").autoIncrement()
    val post = varchar("post", 50)

    override val primaryKey = PrimaryKey(idPos, name = "Position_pkey")

    fun positionToMap(row: ResultRow) : PositionData =
        PositionData(
            idPos = row[idPos],
            post = row[post]
        )
}

object Request : Table() {
    val idReq = integer("idReq").autoIncrement()
    override val primaryKey = PrimaryKey(idReq, name = "Request_pkey")

    fun requestToMap(row: ResultRow) : RequestData =
        RequestData(
            idReq = row[idReq]
        )
}

object Signature_Contract : Table() {
    val idSign = integer("idSign").autoIncrement()
    val typeSign = varchar("typeSign", 40)
    val dateSign = datetime("dateSign")
    val idContract = (integer("idContract") references Contract.idContr)
    val idEmployer = (integer("idEmployer") references Employer.idEmp)

    override val primaryKey = PrimaryKey(idSign, name = "SignatureContract_pkey")

    fun signContractToMap(row: ResultRow) : SignContractData =
        SignContractData(
            idSign = row[idSign],
            typeSign = row[typeSign],
            dateSign = row[dateSign].toString("dd-MM-yyyy"),
            idContract = row[idContract],
            idEmployer = row[idEmployer]
        )
}

object Virtual_Server : Table() {
    val idVs = integer("idVS").autoIncrement()
    val idRequest = (integer("idRequest") references Request.idReq)

    override val primaryKey = PrimaryKey(idVs, name = "VirtualServer_pkey")

    fun virtServerToMap(row: ResultRow) : VirtServerData =
        VirtServerData(
            idVs = row[idVs],
            idRequest = row[idRequest]
        )
}



