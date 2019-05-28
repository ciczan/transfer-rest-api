package com.ciczan

import com.ciczan.persistence.H2DAO
import javax.ws.rs.core.Application

class JerseyApplication: Application() {
    override fun getSingletons(): MutableSet<Any> {
        return mutableSetOf(AccountResource(H2DAO.instance), TransferResource(H2DAO.instance))
    }
}
