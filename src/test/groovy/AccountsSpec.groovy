import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import groovyx.net.http.RESTClient
import spock.lang.Stepwise
import spock.lang.Title
import static groovyx.net.http.ContentType.*

@Title("Account resource specification")
@Narrative("""The accounts in this 
In this API adding an account means registering an account for transfers.
""")
@Stepwise
class AccountsSpec extends Specification {

    @Shared
    RESTClient client

    final String EXCHANGE_ACC_ALIAS = "Stock Exchange"

    def setupSpec() {
        TestNettyServer.startServer()
        client = new RESTClient("http://localhost:8080")
    }

    def cleanupSpec() {
        TestNettyServer.stopServer()
    }

    def "GET to /accounts returns the list of registered accounts"() {
        given: "A call for the accounts"
        HttpResponseDecorator resp = client.get(path: "accounts")

        expect: "A good response and "
        resp.status == 200
        resp.data instanceof List
    }

    def "POST adds accounts to the list"() {

        given: "A new account"
        def newAccount = [path: "accounts",
                          body: [alias: EXCHANGE_ACC_ALIAS, owner: "TheExchange", number: 9],
                          requestContentType: JSON]

        when: "The account is posted"
        HttpResponseDecorator resp = client.post(newAccount)

        then: "The response is a 201 with the URI of the new resource"
        resp.status == 201
        def newLocation = resp.getFirstHeader("Location").value
        newLocation.contains("accounts")

        and: "The new resource is returned by the location"
        HttpResponseDecorator lResp = client.get(path: newLocation)
        lResp.status == 200
        lResp.data instanceof Map
        lResp.data["alias"] == EXCHANGE_ACC_ALIAS

        and: "The new resource is listed in accounts"
        HttpResponseDecorator listResp = client.get(path: "accounts")
        listResp.data.any{ it["alias"] == EXCHANGE_ACC_ALIAS }
    }

    def "Delete by the alias deletes the account"() {
        given: "A registered account"
        assert client.get(path: "accounts/${EXCHANGE_ACC_ALIAS}").data["alias"] == EXCHANGE_ACC_ALIAS

        when: "A delete request is made"
        HttpResponseDecorator delResp = client.delete(
                path: "accounts/${EXCHANGE_ACC_ALIAS}"
        )
        assert delResp.status == 200 : "Delete failed"
        client.get(path: "accounts/${EXCHANGE_ACC_ALIAS}")

        then: "The account is not available anymore"
        HttpResponseException exe = thrown()
        exe.response.status == 404
    }

    def "Attempting to Delete an non existent account returns a HTTP 204 (No Content)"() {
        given: "An account that is not registered"
        def acc = URLEncoder.encode("SomeOtherAccount", "UTF-8")
        try {
            HttpResponseDecorator getResp = client.get(path: "accounts/$acc")
            assert getResp.status == 200 : "Account actually exists"
        } catch (HttpResponseException exe) {
            //Its ok.
        }

        when: "A DELETE request is made"
        HttpResponseDecorator delResp = client.delete(path: "accounts/$acc")

        then: "The response is a 204 status"
        delResp.status == 204
    }

    def "A PUT request of a new account should create the account and return a HTTP 201 (created)"() {
        given: "An account that is not registered"
        def acc = URLEncoder.encode("SomeOtherAccount", "UTF-8")
        try {
            HttpResponseDecorator getResp = client.get(path: "accounts/$acc")
            assert getResp.status == 200 : "Account actually exists"
        } catch (HttpResponseException exe) {
            //Its ok.
        }

        when: "A PUT request is made"
        def newAccount = [path: "accounts",
                          body: [alias: acc, owner: acc+"Owner", number: 9],
                          requestContentType: JSON]

        HttpResponseDecorator putResp = client.put(path: "accounts/$acc", body: newAccount)

        then: "The response is a 201 status"
        putResp.status == 201

        and: "The new account was created"
        HttpResponseDecorator getRespCheck = client.get(path: "accounts/$acc")
        getRespCheck.status == 200
        getRespCheck.data instanceof Map
        getRespCheck.data["alias"] == "SomeOtherAccount"

    }


}  