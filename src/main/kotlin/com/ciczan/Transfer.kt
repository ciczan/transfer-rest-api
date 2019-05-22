package com.ciczan

enum class TransferStatus { NEW, REJECTED, PENDING, DONE}

data class Transfer(val from: String, val to: String, val value: Int, val ccy: String) {
    var id = 0
    var status = TransferStatus.NEW
}