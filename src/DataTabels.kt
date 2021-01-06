package com.example

import com.example.Address.primaryKey
import org.joda.time.DateTime
import java.util.*

data class GraphQLRequest(val query: String? = null)

data class AccountData(
    var idAccount: Int? = null,
    var login: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var token: String? = null
)

data class AddressData(
    val idAdr : Int? = null,
    var name : String? = null,
    var idCustomer : Int? = null,
    var idCity : Int? = null
)

data class CityData(
    val idCity: Int? = null,
    var name: String? = null
)

data class CompositionData(
    val idCompos: Int? = null,
    var idContract: Int? = null,
    var idRequest: Int? = null
)

data class ContractData(
    val idContr: Int? = null
)

data class CustomerData(
    var idCust: Int? = null,
    var FIO: String? = null,
    var idCustomer: Int? = null,
    var idRequest: Int? = null
)

data class EmployerData(
    val idEmp: Int? = null,
    var name: String? = null,
    var idPosition: Int? = null
)

data class ExecutionData(
    val idExec: Int? = null,
    var status: Boolean? = null,
    var execDate: String? = null,
    var idComposition: Int? = null
)

data class ExecutionEmployerData(
    val idExecEmp: Int? = null,
    var idExecution: Int? = null,
    var idEmp: Int? = null
)

data class PriceData(
    val idPrice: Int? = null,
    var dateStart: String? = null,
    var dateEnd: String? = null,
    var price: Float? = null,
    var idOption: Int? = null
)

data class ParticipantData(
    val idPart: Int? = null,
    var name: String? = null,
    var idCustomer: Int? = null,
    var idContract: Int? = null
)

data class PaymentData(
    val idPay: Int? = null,
    var size: Float? = null,
    var payDate: String? = null,
    var idContract: Int? = null
)

data class PositionData(
    val idPos: Int? = null,
    var post: String? = null
)

data class RequestData(
    val idReq: Int? = null
)

data class SignatureContractData(
    val idSign: Int? = null,
    var typeSign: String? = null,
    var dateSign : String? = null,
    var idContract: Int? = null
)

data class VirtualServerData(
    var idVS: Int? = null,
    var idRequest: Int? = null
)

data class Count(
    val count: Int? = null // AVG MAX MIN и т.д.
)