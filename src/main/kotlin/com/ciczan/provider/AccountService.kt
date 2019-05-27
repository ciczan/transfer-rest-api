package com.ciczan.provider

import com.ciczan.domain.Account
import com.ciczan.domain.User

interface AccountService {

    fun getAllAccounts(user: User): Collection<Account>

    fun deleteAccount(user: User)

    fun insertAccount(user: User)

    fun getAccount(user: User, alias: String): Account?

}

object FixedAccountsService: AccountService {

    private val myAccount = Account("My Account", "Cicero", "11012 000316186 01")
    private val wifeAccount = Account("Wife", "Alice", "11012 000316920 05")
    private var accounts = mutableMapOf("My Account" to myAccount, "Wife" to wifeAccount)

    override fun insertAccount(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccount(user: User, alias: String): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getAllAccounts(user: User): Collection<Account> {
        return this.accounts.values
    }

    override fun deleteAccount(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
