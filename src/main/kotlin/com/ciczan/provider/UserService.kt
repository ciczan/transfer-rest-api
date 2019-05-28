package com.ciczan.provider

import com.ciczan.domain.User

interface UserService {
    fun getUser(name: String): User?
}

object FixedUserService :  UserService {

    val user1 = User("Cicero", "Brazil")

    override fun getUser(name: String): User {
        return user1
    }

}