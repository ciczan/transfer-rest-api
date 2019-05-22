package com.ciczan

import com.ciczan.provider.FixedTransferProvider
import com.ciczan.provider.TransferProvider
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("transfers")
@Produces(MediaType.APPLICATION_JSON)
class TransferResource {

    val provider: TransferProvider = FixedTransferProvider

    @GET
    fun listTransfers() = provider.allTransfers()

}