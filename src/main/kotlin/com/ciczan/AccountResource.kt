package com.ciczan

import com.ciczan.domain.Account
import com.ciczan.domain.User
import com.ciczan.provider.AccountService
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("accounts")
@Produces(APPLICATION_JSON)
class AccountResource(val service: AccountService) {

    private val ciceroUser = User("Cicero", "Brazil")

    private val myAccount = Account("My Account", "Cicero",  "11012 000316186 01", "EUR")
    private val wifeAccount = Account( "Wife", "Alice", "11012 000316920 05", "EUR")
    private var accounts = mutableMapOf("My Account" to myAccount, "Wife" to wifeAccount)

    @GET
    @Path("{alias}")
    fun getAccount(@PathParam("alias") alias: String): Account? {
        val decodedAlias = URLDecoder.decode(alias, "UTF-8")
        val value = accounts[decodedAlias]

        return when (value) {
            is Account -> value
            else -> throw WebApplicationException(404)
        }
    }

    @GET
    fun getAccounts(): Collection<Account> {
        return this.accounts.values
    }

    @POST
    @Consumes(APPLICATION_JSON)
    fun createAccount(acc: Account, @Context ui: UriInfo): Response {

        acc.userName = ciceroUser.name

        service.insertAccount(acc)

        //accounts[acc.alias] = acc
        //https://jersey.github.io/documentation/latest/representations.html#d0e6315

        val encodedAlias = URLEncoder.encode(acc.alias, "UTF-8")

        return Response.created(URI.create(ui.requestUri.toString() + "/" + encodedAlias)).build()
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("{alias}")
    fun updateAccount(@PathParam("alias") alias: String, acc: Account) {
        accounts[alias] = acc
    }

    @DELETE
    @Path("{alias}")
    fun deleteAccount(@PathParam("alias") alias: String): Account? {
        return accounts.remove(alias)
    }

}