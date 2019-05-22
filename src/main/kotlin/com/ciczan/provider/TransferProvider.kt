package com.ciczan.provider

import com.ciczan.Transfer

interface TransferProvider {
    fun allTransfers(): List<Transfer>

}

object FixedTransferProvider: TransferProvider {

    private val transfer1 = Transfer("Cicero", "Zappo", 2000, "GBP")
    private val transfer2 = Transfer("Zappo", "Cicero", 2000, "GBP")

    override fun allTransfers(): List<Transfer> = listOf(transfer1, transfer2)

}