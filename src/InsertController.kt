package com.example

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class InsertController {
    fun insertAccount(login: String, email: String,
                      password: String, phone: String): String {
        transaction {
            Account.insert {
                it[Account.login] = login
                it[Account.email] = email
                it[Account.password] = password
                it[Account.phone] = phone
            }
        }
        return email
    }

    fun addToken(token : String, email : String) : String {
        transaction {
            Account.update({ Account.email eq email }) {
                it[Account.token] = token
            }
        }
        return token
    }
}