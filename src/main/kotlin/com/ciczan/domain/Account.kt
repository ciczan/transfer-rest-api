package com.ciczan.domain

data class Account(val user: User, val alias: String, val owner: String, val number: String, val ccy: String) {
    var id = 0
}