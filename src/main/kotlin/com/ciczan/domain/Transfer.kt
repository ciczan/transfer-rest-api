package com.ciczan.domain

enum class TransferStatus { PENDING,  REJECTED,  SETTLED}

data class Transfer(val from: String, val to: String, val value: Int, val ccy: String) {
    var id = 0
    var status = TransferStatus.PENDING
}