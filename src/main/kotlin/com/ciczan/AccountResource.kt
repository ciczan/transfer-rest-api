package com.ciczan

import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("accounts")
@Produces(APPLICATION_JSON)
class AccountResource {


    val trucoAccount = Account("Cicero", "11012 000316186 01")
    val trucoAccount2 = Account("Alice", "11012 000316920 05")
    var accountList = listOf(trucoAccount, trucoAccount2)

    @GET
    fun getAccounts(): List<Account> {
        return accountList
    }

    @POST
    fun createAccount(acc: Account) {
        accountList += acc
        //https://jersey.github.io/documentation/latest/representations.html#d0e6315
    }

}