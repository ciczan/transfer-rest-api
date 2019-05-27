package com.ciczan

import com.ciczan.provider.FixedTransferService
import com.ciczan.provider.TransferService
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("transfers")
@Produces(MediaType.APPLICATION_JSON)
class TransferResource {

    val service: TransferService = FixedTransferService

    @GET
    fun listTransfers() = service.allTransfers()

}