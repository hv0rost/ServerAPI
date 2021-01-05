package com.example

import com.apurebase.kgraphql.KGraphQL
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TransactionController {

    private val transaction = InsertController()

    val accountController = KGraphQL.schema {
        query("getAccount") {
            resolver {  ->
                transaction {
                    Account.selectAll().map { Account.accountToMap(it) }
                }
            }
        }
        mutation("postAccount") {
            resolver { login: String, email: String,
                       password: String, phone: String ->
                transaction.insertAccount(login, email, password, phone)
            }
        }
        mutation("deleteAccount"){
            resolver{ idAccount : Int ->
                transaction {
                    Account.deleteWhere { Account.idAccount eq idAccount }
                }
            }
        }
        mutation("updateAccount") {
            resolver { idAccount : Int, login: String, email: String,
                       password: String, phone: String ->
                transaction {
                    Account.update({ Account.idAccount eq idAccount }) {
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