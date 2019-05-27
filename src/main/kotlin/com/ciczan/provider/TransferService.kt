package com.ciczan.provider

import com.ciczan.domain.Transfer

interface TransferService {
    fun allTransfers(): List<Transfer>

}

object FixedTransferService: TransferService {

    private val transfer1 = Transfer("Cicero", "Zappo", 2000, "GBP")
    private val transfer2 = Transfer("Zappo", "Cicero", 2000, "GBP")

    override fun allTransfers(): List<Transfer> = listOf(transfer1, transfer2)

}