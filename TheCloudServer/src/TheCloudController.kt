package com.example

import io.ktor.application.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.Date
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import kotlin.collections.ArrayList

class TheCloudController {
    //Get Part
    fun getAll(): ArrayList<Cities> {
        val city: ArrayList<Cities> = arrayListOf() // .toString("dd-MM-yyyy") для дат
        transaction {
            City.selectAll().map {
                city.add(
                    Cities(
                        idCity = it[City.idCity],
                        name = it[City.name]
                    )
                )
            }
        }
        return city
    }

    fun getAddressName(): Pair<ArrayList<Addresses>, ArrayList<Cities>> {
        val address: ArrayList<Addresses> = arrayListOf()
        val city: ArrayList<Cities> = arrayListOf()
        transaction {
            Join(
                Address, City, onColumn = Address.idCity, otherColumn = City.idCity,
                joinType = JoinType.INNER,
            ).slice(Address.idAdr, Address.name, City.name)
                .select { Address.idAdr.greaterEq(2) and City.name.like("A%") }.forEach() {
                    address.add(
                        Addresses(
                            idAdr = it[Address.idAdr],
                            name = it[Address.name]
                        )
                    )
                    city.add(
                        Cities(
                            name = it[City.name]
                        )
                    )
                }
        }
        return Pair(address, city)
    }

    fun getPaymentCustomer(): Triple<ArrayList<Addresses>, ArrayList<Customers>, ArrayList<Payments>> {
        val address: ArrayList<Addresses> = arrayListOf()
        val customer: ArrayList<Customers> = arrayListOf()
        val payment: ArrayList<Payments> = arrayListOf()

        transaction {
            (Address.leftJoin(Customer).leftJoin(Participants).leftJoin(Contract).leftJoin(Payment)
                    ).slice(Address.name, Customer.FIO, Payment.size)
                .select { Address.name.eq("Lenina") and Payment.size.less(500) }.forEach() {
                    address.add(
                        Addresses(
                            name = it[Address.name]
                        )
                    )
                    customer.add(
                        Customers(
                            FIO = it[Customer.FIO]
                        )
                    )
                    payment.add(
                        Payments(
                            size = it[Payment.size]
                        )
                    )
                }
        }
        return Triple(address, customer, payment)
    }

    fun getTypeSign(): Triple<ArrayList<Customers>, ArrayList<Cities>, ArrayList<SignatureContracts>> {
        val customer: ArrayList<Customers> = arrayListOf()
        val city: ArrayList<Cities> = arrayListOf()
        val signatureContract: ArrayList<SignatureContracts> = arrayListOf()

        transaction {
            (Customer.leftJoin(Address).leftJoin(City).leftJoin(Participants).leftJoin(Contract)
                .leftJoin(Signature_Contract)
                    ).slice(Customer.FIO, City.name, Signature_Contract.typeSign)
                .select {
                    Signature_Contract.typeSign.eq("digital") and
                            Customer.FIO.eq("Borisov")
                }.forEach() {
                    customer.add(
                        Customers(
                            FIO = it[Customer.FIO]
                        )
                    )
                    city.add(
                        Cities(
                            name = it[City.name]
                        )
                    )
                    signatureContract.add(
                        SignatureContracts(
                            typeSign = it[Signature_Contract.typeSign]
                        )
                    )
                }
        }
        return Triple(customer, city, signatureContract)
    }

    fun getPostDate(): Triple<ArrayList<Employers>, ArrayList<Positions>, ArrayList<SignatureContracts>> {
        val employer: ArrayList<Employers> = arrayListOf()
        val position: ArrayList<Positions> = arrayListOf()
        val signatureContract: ArrayList<SignatureContracts> = arrayListOf()

        transaction {
            (Employer.leftJoin(Signature_Contract).leftJoin(Position)
                    ).slice(Employer.name, Position.post, Signature_Contract.dateSign)
                .select {
                    (Signature_Contract.dateSign.between(DateTime("2020-8-31"), DateTime("2020-12-31")) and
                            Position.post.like("Android%"))
                }.forEach() {
                    employer.add(
                        Employers(
                            name = it[Employer.name]
                        )
                    )
                    position.add(
                        Positions(
                            post = it[Position.post]
                        )
                    )
                    signatureContract.add(
                        SignatureContracts(
                            dateSign = it[Signature_Contract.dateSign].toString("dd-MM-yyyy")
                        )
                    )
                }
        }
        return Triple(employer, position, signatureContract)
    }

    fun getIOSDev(): Triple<ArrayList<Employers>, ArrayList<Positions>, ArrayList<Executions>> {
        val employer: ArrayList<Employers> = arrayListOf()
        val position: ArrayList<Positions> = arrayListOf()
        val execution: ArrayList<Executions> = arrayListOf()
        transaction {
            (Employer.leftJoin(Position).leftJoin(Execution_Employer).leftJoin(Execution)
                    ).slice(Employer.name, Position.post, Execution.status, Execution.execDate)
                .select { Position.post.like("IOS-%") and Execution.status.eq(false) }.forEach() {
                    employer.add(
                        Employers(
                            name = it[Employer.name]
                        )
                    )
                    position.add(
                        Positions(
                            post = it[Position.post]
                        )
                    )
                    execution.add(
                        Executions(
                            status = it[Execution.status],
                            execDate = it[Execution.execDate].toString("dd-MM-yyyy")
                        )
                    )
                }
        }
        return Triple(employer, position, execution)
    }

    fun getStatusCustomer(): Pair<ArrayList<Customers>, ArrayList<Executions>> {
        val customer: ArrayList<Customers> = arrayListOf()
        val execution: ArrayList<Executions> = arrayListOf()
        transaction {
            (Customer.leftJoin(Request).leftJoin(Composition_Contract).leftJoin(Execution)
                    ).slice(Customer.FIO, Customer.phone, Execution.status)
                .select { Execution.status.eq(true) }.forEach() {
                    customer.add(
                        Customers(
                            FIO = it[Customer.FIO],
                            phone = it[Customer.phone]

                        )
                    )
                    execution.add(
                        Executions(
                            status = it[Execution.status]
                        )
                    )
                }
        }
        return Pair(customer, execution)
    }

    fun getVsSize(): Pair<ArrayList<VirtualServers>, ArrayList<Payments>> {
        val virtualServer: ArrayList<VirtualServers> = arrayListOf()
        val payment: ArrayList<Payments> = arrayListOf()
        transaction {
            (Virtual_Server.leftJoin(Request).leftJoin(Composition_Contract).leftJoin(Contract).leftJoin(Payment)
                    ).slice(Virtual_Server.idVS, Payment.size)
                .select { Payment.size.greaterEq(600) }.forEach() {
                    virtualServer.add(
                        VirtualServers(
                            idVS = it[Virtual_Server.idVS]
                        )
                    )
                    payment.add(
                        Payments(
                            size = it[Payment.size]
                        )
                    )
                }
        }
        return Pair(virtualServer, payment)
    }

    fun getPart(): Pair<ArrayList<Participant>, ArrayList<Executions>> {
        val participant: ArrayList<Participant> = arrayListOf()
        val execution: ArrayList<Executions> = arrayListOf()
        transaction {
            (Participants.leftJoin(Contract).leftJoin(Composition_Contract).leftJoin(Execution)
                    ).slice(Participants.name, Execution.execDate)
                .select { Execution.execDate.between(DateTime("2001-1-1"), DateTime("2001-12-31")) }
                .groupBy(Participants.name, Execution.execDate).forEach() {
                    participant.add(
                        Participant(
                            name = it[Participants.name]
                        )
                    )
                    execution.add(
                        Executions(
                            execDate = it[Execution.execDate].toString("dd-MM-yyyy")
                        )
                    )
                }
        }
        return Pair(participant, execution)
    }

    fun getCustomerSign(): Triple<ArrayList<Customers>, ArrayList<Addresses>, ArrayList<SignatureContracts>> {
        val address: ArrayList<Addresses> = arrayListOf()
        val customer: ArrayList<Customers> = arrayListOf()
        val signatureContract: ArrayList<SignatureContracts> = arrayListOf()

        transaction {
            (Address.leftJoin(Customer).leftJoin(Request).leftJoin(Composition_Contract)
                .leftJoin(Contract).leftJoin(Signature_Contract)
                    ).slice(Address.name, Customer.FIO, Signature_Contract.typeSign)
                .select { Signature_Contract.typeSign.like("man%") }.forEach() {
                    address.add(
                        Addresses(
                            name = it[Address.name]
                        )
                    )
                    customer.add(
                        Customers(
                            FIO = it[Customer.FIO]
                        )
                    )
                    signatureContract.add(
                        SignatureContracts(
                            typeSign = it[Signature_Contract.typeSign]
                        )
                    )
                }
        }
        return Triple(customer, address, signatureContract)
    }

    fun getDateSign(): Pair<ArrayList<Participant>, ArrayList<SignatureContracts>> {
        val participant: ArrayList<Participant> = arrayListOf()
        val signatureContract: ArrayList<SignatureContracts> = arrayListOf()

        transaction {
            (Contract.leftJoin(Participants).leftJoin(Signature_Contract)
                    ).slice(Participants.name, Signature_Contract.typeSign, Signature_Contract.dateSign)
                .select { Participants.name.like("Fast%") }.forEach() {
                    participant.add(
                        Participant(
                            name = it[Participants.name]
                        )
                    )
                    signatureContract.add(
                        SignatureContracts(
                            typeSign = it[Signature_Contract.typeSign],
                            dateSign = it[Signature_Contract.dateSign].toString("dd-MM-yyyy")
                        )
                    )
                }
        }
        return Pair(participant,signatureContract)
    }

    fun getExecEmail(): Pair< ArrayList<Customers>, ArrayList<Executions>> {
        val customer: ArrayList<Customers> = arrayListOf()
        val execution: ArrayList<Executions> = arrayListOf()

        transaction {
            (Customer.leftJoin(Request).leftJoin(Composition_Contract).leftJoin(Execution)
                    ).slice(Customer.email, Customer.FIO, Execution.status)
                .select { Execution.status.eq(true) }.forEach() {
                    customer.add(
                        Customers(
                            email = it[Customer.email],
                            FIO = it[Customer.FIO]
                        )
                    )
                    execution.add(
                        Executions(
                            status = it[Execution.status]
                        )
                    )
                }
        }
        return Pair(customer, execution)
    }

    fun getEmployersName(): Pair< ArrayList<Customers>, ArrayList<Employers>,> {
        val customer: ArrayList<Customers> = arrayListOf()
        val employer: ArrayList<Employers> = arrayListOf()
        val executionEmployer: ArrayList<ExecutionEmployers> = arrayListOf()

        transaction {
            (Customer.leftJoin(Request).leftJoin(Composition_Contract).leftJoin(Execution)
                .leftJoin(Execution_Employer).leftJoin(Employer)
                    ).slice(Customer.FIO,Customer.idCust, Employer.name)
                .select { Customer.idCust.between(1,3) }.forEach() {
                    customer.add(
                        Customers(
                            FIO = it[Customer.FIO],
                            idCustomer = it[Customer.idCust]
                        )
                    )
                    employer.add(
                        Employers(
                            name = it[Employer.name]
                        )
                    )

                }
        }
        return Pair(customer, employer)
    }

//Insert Part
    fun cityInsert(city : Cities){
        transaction{
            City.insert {
                it[name] = city.name!!
            }
        }
    }

    fun virtualServerInsert(vs : VirtualServers){
        transaction {
            Virtual_Server.insert{
                it[idRequest] = vs.idRequest!!
            }
        }
    }

    fun signatureContractInsert(signContr : SignatureContracts){
        transaction {
            Signature_Contract.insert {
                it[typeSign] = signContr.typeSign!!
                it[dateSign] = DateTime(signContr.dateSign!!)
                it[idContract] = signContr.idContract!!
            }
        }
    }

    fun requestInsert(request: Requests){
        transaction {
            Request.insert {

            }
        }
    }

    fun positionInsert(positions: Positions){
        transaction {
            Position.insert {
                it[post] = positions.post!!
            }
        }
    }

    fun paymentInsert(payments: Payments){
        transaction {
            Payment.insert {
                it[size] = payments.size!!
                it[payDate] = DateTime(payments.payDate!!)
                it[idContract] = payments.idContract!!
            }
        }
    }

    fun participantsInsert(participant: Participant) {
        transaction {
            Participants.insert {
                it[name] = participant.name!!
                it[idCustomer] = participant.idCustomer!!
                it[idContract] = participant.idContract!!
            }
        }
    }

    fun optionPricesInsert(optionPrice: OptionPrices){
        transaction {
            Option_Price.insert {
                it[dateStart] = DateTime(optionPrice.dateStart!!)
                it[dateEnd] = DateTime(optionPrice.dateEnd!!)
                it[price] = optionPrice.price!!
                it[idOption] = optionPrice.idOption!!
            }
        }
    }

    fun executionEmployerInsert(executionEmployer: ExecutionEmployers){
        transaction {
            Execution_Employer.insert {
                it[idExecution] = executionEmployer.idExecution!!
                it[idEmp] = executionEmployer.idEmp!!
            }
        }
    }

    fun executionInsert(execution: Executions){
        transaction {
            Execution.insert {
                it[status] = execution.status!!
                it[execDate] = DateTime(execution.execDate!!)
                it[idComposition] = execution.idComposition!!
            }
        }
    }

    fun employerInsert(employer: Employers){
        transaction {
            Employer.insert {
                it[name] = employer.name!!
                it[idPosition] = employer.idPosition!!
            }
        }
    }

    fun customerInsert(customer: Customers){
        transaction {
            Customer.insert {
                it[idCust] = customer.idCust!!
                it[FIO] = customer.FIO!!
                it[email] = customer.email!!
                it[phone] = customer.phone!!
                it[idCustomer] = customer.idCustomer!!
                it[idRequest] = customer.idRequest!!
            }
        }
    }

    fun contractInsert(contract: Contracts){
        transaction {
            Contract.insert {
                it[idContr] = contract.idContr!!
            }
        }
    }

    fun compositionContractInsert(compositionContract: CompositionContracts){
        transaction {
            Composition_Contract.insert {
                it[idContract] = compositionContract.idContract!!
                it[idRequest] = compositionContract.idRequest!!
            }
        }
    }

    fun addressInsert(address: Addresses){
        transaction {
            Address.insert {
                it[name] = address.name!!
                it[idCustomer] = address.idCustomer!!
                it[idCity] = address.idCity!!
            }
        }
    }
}