package com.example

import com.example.Address.primaryKey
import org.joda.time.DateTime
import java.util.*

data class Addresses(
    val idAdr : Int? = null,
    var name : String? = null,
    var idCustomer : Int? = null,
    var idCity : Int? = null
)

data class Cities(
    val idCity: Int? = null,
    var name: String? = null
)

data class CompositionContracts(
    val idCompos: Int? = null,
    var idContract: Int? = null,
    var idRequest: Int? = null
)

data class Contracts(
    val idContr: Int? = null
)

data class Customers(
    var idCust: Int? = null,
    var FIO: String? = null,
    var idCustomer: Int? = null,
    var idRequest: Int? = null
)

data class Accounts(
    var idAccount: Int? = null,
    var login: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone: String? = null
)

data class Employers(
    val idEmp: Int? = null,
    var name: String? = null,
    var idPosition: Int? = null
)

data class Executions(
    val idExec: Int? = null,
    var status: Boolean? = null,
    var execDate: String? = null,
    var idComposition: Int? = null
)

data class ExecutionEmployers(
    val idExecEmp: Int? = null,
    var idExecution: Int? = null,
    var idEmp: Int? = null
)

data class OptionPrices(
    val idPrice: Int? = null,
    var dateStart: String? = null,
    var dateEnd: String? = null,
    var price: Float? = null,
    var idOption: Int? = null
)

data class Participant(
    val idPart: Int? = null,
    var name: String? = null,
    var idCustomer: Int? = null,
    var idContract: Int? = null
)

data class Payments(
    val idPay: Int? = null,
    var size: Float? = null,
    var payDate: String? = null,
    var idContract: Int? = null
)

data class Positions(
    val idPos: Int? = null,
    var post: String? = null
)

data class Requests(
    val idReq: Int? = null
)

data class SignatureContracts(
    val idSign: Int? = null,
    var typeSign: String? = null,
    var dateSign : String? = null,
    var idContract: Int? = null
)

data class VirtualServers(
    var idVS: Int? = null,
    var idRequest: Int? = null
)

data class Count(
    val count: Int? = null // AVG MAX MIN и т.д.
)