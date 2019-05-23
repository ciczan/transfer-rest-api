package com.ciczan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import javax.ws.rs.ext.ContextResolver

object NettyServer {
    @JvmStatic
    fun main(args: Array<String>) {
        val resourceConfig = ResourceConfig.forApplication(JerseyApplication())
                .register(ContextResolver<ObjectMapper> { ObjectMapper().registerModule(KotlinModule()) })


        resourceConfig.register(createConsoleLoggingFeature())

        val server = NettyHttpContainerProvider.createHttp2Server(URI.create("http://localhost:8080/"), resourceConfig, null)
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { server.close() }))
    }

    private fun createConsoleLoggingFeature(): LoggingFeature {
        val logger: Logger = Logger.getLogger("JerseyRequests")
        logger.level = Level.ALL

        val cHandler = ConsoleHandler()
        cHandler.level = Level.ALL
        logger.addHandler(cHandler)

        return LoggingFeature(logger, LoggingFeature.Verbosity.PAYLOAD_TEXT)
    }
}
