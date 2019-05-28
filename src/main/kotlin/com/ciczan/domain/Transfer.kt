package com.ciczan.domain

import java.util.*

enum class TransferStatus { PENDING,  REJECTED,  SETTLED}

data class Transfer(val from: String, val to: String, val value: Int, val creation: Date) {
    var id = 0
    var status = TransferStatus.PENDING
}