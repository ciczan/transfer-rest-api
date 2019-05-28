package com.ciczan.persistence

import com.ciczan.domain.Account
import com.ciczan.domain.Transfer
import com.ciczan.domain.User
import com.ciczan.provider.AccountService
import com.ciczan.provider.TransferService
import com.ciczan.provider.UserService
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class H2DAO: AccountService, UserService, TransferService {

    //So we have the same database
    companion object {
        val instance: H2DAO  = H2DAO()
    }

    init {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(Users, Accounts, Transfers)
            addLogger(StdOutSqlLogger)
        }
    }


    override fun getUser(name: String): User? {
        var user: User? = null

        transaction {
           val result = UserRow.find { Users.name eq name }
            if (result.count() > 0) {
                val userRow = result.first()
                user = User(name = userRow.name, country = userRow.country)
            }
       }

        return user
    }

    fun addUser(user: User): User {
        transaction {
            addLogger(StdOutSqlLogger)
            val ur = UserRow.new {
                name = user.name
                country = user.country
            }
            user.id = ur.id.value
        }
        return user
    }

    override fun allTransfers(): List<Transfer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAccount(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertAccount(account: Account): Account {
        transaction {
            val ar = AccountRow.new {
                user = UserRow[account.user.id].id
                alias = account.alias
                owner = account.owner
                number = account.number
            }
            account.id = ar.id.value
        }
        return account
    }

    override fun getAccount(user: User, alias: String): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllAccounts(user: User): Collection<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}