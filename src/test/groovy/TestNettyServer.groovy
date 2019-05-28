import com.ciczan.JerseyApplication
import com.ciczan.domain.Account
import com.ciczan.domain.User
import com.ciczan.persistence.H2DAO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.netty.channel.Channel
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider

import javax.ws.rs.ext.ContextResolver
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger


class TestNettyServer {

    private static Channel server

    static def startServer() {

        H2DAO dao = H2DAO.instance

        ///Add test data
        def user = dao.addUser(new User("Pierre", "France"))

        Account myAccount = new Account(user, "My Account", "Pierre",  "11012 000316186 01", "EUR")
        Account wifeAccount = new Account(user, "Wife", "Alice", "11012 000316920 05", "EUR")
        dao.insertAccount(myAccount)
        dao.insertAccount(wifeAccount)

        def contextResolver = new ContextResolver<ObjectMapper>() {
            ObjectMapper mapper = new ObjectMapper().registerModule(new KotlinModule())
            ObjectMapper getContext(Class<?> type) {this.mapper}
        }
        def resourceConfig = ResourceConfig.forApplication(new JerseyApplication()).register(contextResolver)

        resourceConfig.register(createConsoleLoggingFeature())

        server = NettyHttpContainerProvider.createHttp2Server(URI.create("http://localhost:8080/"), resourceConfig, null)
        Runtime.getRuntime().addShutdownHook { -> server.close()}

    }

    static def stopServer() {
        if (server != null)
            server.close()
    }

    static private LoggingFeature createConsoleLoggingFeature() {
        Logger logger = Logger.getLogger("JerseyRequests")
        logger.level = Level.ALL

        ConsoleHandler cHandler = new ConsoleHandler()
        cHandler.level = Level.ALL
        logger.addHandler(cHandler)

        return new LoggingFeature(logger, LoggingFeature.Verbosity.PAYLOAD_TEXT)
    }

    static void main(String[] args) {
        startServer()
    }

}
