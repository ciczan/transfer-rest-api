package com.ciczan.domain

data class Account(val alias: String, val owner: String, val number: String, val ccy: String) {
    var userName: String = ""
    var id = 0
}