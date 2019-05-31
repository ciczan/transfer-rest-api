package com.ciczan.persistence

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import javax.jws.soap.SOAPBinding

object Users: IdTable<String>() {
    override val id = varchar("name", 50).entityId()
    val country = varchar("country", 50)
}

class UserRow(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, UserRow>(Users)

    var name by Users.id
    var country by Users.country
}

object Accounts: IntIdTable() {
    val user = reference("user", Users)
    val alias = varchar("alias", 200).uniqueIndex()
    val owner = varchar("owner", 50)
    val number = varchar("number", 50)
    val currency = varchar("currency", 3)
}

class AccountRow(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountRow>(Accounts)

    var user by Accounts.user
    var alias by Accounts.alias
    var owner by Accounts.owner
    var number by Accounts.number
    var currency by Accounts.currency
}


object Transfers: IntIdTable() {

    val fromAccount = reference("from_account", Accounts)
    val toAccount = reference("to_account", Accounts)
    //In cents. Assuming all currencies have 2 decimal places for now.
    val amount = integer("amount")
    val creation = datetime("creation")
    val status = integer("status")
}

class TransferRow(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TransferRow>(Transfers)

    var fromAccount by Transfers.fromAccount
    var toAccount by Transfers.toAccount
    var amount by Transfers.amount
    var creation by Transfers.creation
    var status by Transfers.status
}



fun main(args: Array<String>) {

    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Users, Accounts, Transfers)

        val firstUser = UserRow.new {
            name = EntityID("Pierre", Users)
            country = "France"
        }





    }

}