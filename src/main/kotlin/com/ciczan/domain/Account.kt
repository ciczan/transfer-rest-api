package com.ciczan.domain

data class Account(val user: User, val alias: String, val owner: String, val number: String) {
    var id = 0
}