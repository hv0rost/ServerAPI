package com.example

import com.apurebase.kgraphql.KGraphQL
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import stringToJson

class TransactionController {

    private val transaction = InsertController()

    val tokenController = KGraphQL.schema {
        query("getToken") {
            resolver { email: String ->
                transaction {
                    Account.slice(Account.email).select { Account.email eq email  }
                        .map { Account.getAuth(it) }
                }
            }
        }
        mutation("registration") {
            resolver { login: String, email: String,
                       password: String, phone: String, info: String ->
                transaction.insertAccount(login, email, password, phone, stringToJson(info) )
            }
        }
        mutation("postToken"){
            resolver { token : String , email : String  ->
                transaction.addToken(token, email)
            }
        }
    }
    val accountController = KGraphQL.schema {
        query("getAccount") {
            resolver {token: String  ->
                transaction {
                    Account.select{Account.token eq token}.map { Account.accountToMap(it) }
                }
            }
        }
        query("login") {
            resolver {login: String, email: String, password: String  ->
                transaction {
                    Account.select{((Account.email eq email) and (Account.password eq password)) or
                            ((Account.login eq login) and (Account.password eq password))}
                        .map { Account.accountToMap(it) }
                }
            }
        }
        mutation("deleteAccount"){
            resolver{ token: String ->
                transaction {
                    Account.deleteWhere { Account.token eq token }
                }
            }
        }
        mutation("updateAccount") {
            resolver { token : String, login: String, email: String,
                       password: String, phone: String ->
                transaction {
                    Account.update({ Account.token eq token }) {
                        it[Account.login] = login
                        it[Account.email] = email
                        it[Account.password] = password
                        it[Account.phone] = phone
                    }
                }
            }
        }
    }

    val сustomerController = KGraphQL.schema {
        query("getCustomer") {
            resolver {token: String  ->
                transaction {
                    Account.select{Account.token eq token}.map { Account.accountToMap(it) }
                }
            }
        }
        /*mutation("postCustomer") {
            resolver { login: String, email: String,
                       password: String, phone: String ->
                transaction.insertAccount(login, email, password, phone)
            }
        }*/
        mutation("deleteCustomer"){
            resolver{ token: String ->
                transaction {
                    Account.deleteWhere { Account.token eq token }
                }
            }
        }
        mutation("updateCustomer") {
            resolver { token : String, login: String, email: String,
                       password: String, phone: String ->
                transaction {
                    Account.update({ Account.token eq token }) {
                        it[Account.login] = login
                        it[Account.email] = email
                        it[Account.password] = password
                        it[Account.phone] = phone
                    }
                }
            }
        }
    }


}