package com.ciczan.provider

interface UserService {
    fun getLoggedUser(): String
}

object FixedUserService :  UserService {

    override fun getLoggedUser(): String {
        return "Cicero"
    }

}