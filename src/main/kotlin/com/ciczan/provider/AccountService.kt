package com.ciczan.provider

import com.ciczan.domain.Account
import com.ciczan.domain.User

interface AccountService {

    fun getAllAccounts(user: User): Collection<Account>

    fun deleteAccount(account: Account)

    fun insertAccount(account: Account): Account

    fun getAccount(user: User, alias: String): Account?

}

object FixedAccountsService: AccountService {

    private val ciceroUser = User("Cicero", "Brazil")

    private val myAccount = Account(ciceroUser, "My Account", "Cicero",  "11012 000316186 01")
    private val wifeAccount = Account(ciceroUser, "Wife", "Alice", "11012 000316920 05")
    private var accounts = mutableMapOf("My Account" to myAccount, "Wife" to wifeAccount)

    override fun insertAccount(account: Account): Account {
        accounts[account.alias] = account
        return account
    }

    override fun getAccount(user: User, alias: String): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getAllAccounts(user: User): Collection<Account> {
        return this.accounts.values
    }

    override fun deleteAccount(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
