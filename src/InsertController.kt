package com.example

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class InsertController {
    fun insertAccount(login: String, email: String,
                      password: String, phone: String): Int {
        transaction {
            Account.insert {
                it[Account.login] = login
                it[Account.email] = email
                it[Account.password] = password
                it[Account.phone] = phone
            }
        }
        return 200
    }
}