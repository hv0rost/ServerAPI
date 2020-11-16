package com.example

import com.example.Address.primaryKey
import org.joda.time.DateTime
import java.util.*

data class Addresses(
    var idAdr : Int? = null,
    var name : String,
    var idCustomer : Int? = null,
    var idCity : Int? = null
)

data class Cities(
    val idCity: Int? = null,
    var name: String? = null
)

data class CompositionContracts(
    val idCompos: Int? = null,
    val idContract: Int? = null,
    val idRequest: Int? = null
)

data class Contracts(
    val idContr: Int? = null
)

data class Customers(
    val idCust: Int? = null,
    val FIO: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val idCustomer: Int? = null,
    val idRequest: Int? = null
)

data class Employers(
    val idEmp: Int? = null,
    val name: String? = null,
    val idPosition: Int? = null
)

data class Executions(
    val idExec: Int? = null,
    val status: Boolean? = null,
    val execDate: String? = null,
    val idComposition: Int? = null
)

data class ExecutionEmployers(
    val idExecEmp: Int? = null,
    val idExecution: Int? = null,
    val idEmp: Int? = null
)

data class OptionPrices(
    val idPrice: Int? = null,
    val dateStart: String? = null,
    val dateEnd: String? = null,
    val price: Float? = null,
    val idOption: Int? = null
)

data class Participant(
    val idPart: Int? = null,
    val name: String? = null,
    val idCustomer: Float? = null,
    val idContract: Int? = null
)

data class Payments(
    val idPay: Int? = null,
    val size: Float? = null,
    val payDate: String? = null,
    val idContract: Int? = null
)

data class Positions(
    val idPos: Int? = null,
    val post: String? = null
)

data class Requests(
    val idReq: Int? = null
)

data class SignatureContracts(
    val idSign: Int? = null,
    val typeSign: String? = null,
    val dateSign : String? = null,
    val idContract: Int? = null
)

data class VirtualServers(
    val idVS: Int? = null,
    val idRequest: Int? = null
)

data class Count(
    val count: Int? = null
)