import spock.lang.Narrative
import spock.lang.Specification
import groovyx.net.http.RESTClient
import spock.lang.Stepwise
import spock.lang.Title

@Title("Account resoruce specification")
@Narrative("""The accounts in this 
In this API adding an account means registering an account for transfers.
""")
@Stepwise
class AccountsSpec extends Specification {

    def client = new RESTClient("http://localhost:8080")

    def "Get to accounts should return a List"() {
        given: "A call for the accounts"
        def resp = client.get(path: "accounts")

        expect: "A good response and "
        resp.status == 200
        resp.data instanceof List
    }

    def "Post adds accounts to the list"() {
        given: "A new account"
        def resp = client.post(
                path: "accounts",
                body: [branch: "nove", number: 9],
                requestContentType: "application/json")

        expect: "It to be there"
        resp.status == 204
        prinln(resp.data)
        //def resp = client.get(path: "accounts")
        //resp.data.find { it.number == 9}


    }


}  