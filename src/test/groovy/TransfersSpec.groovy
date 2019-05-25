import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@Title("Tests and describes the behaviour of the transfers resources")
@Stepwise
class TransfersSpec extends Specification{

    @PendingFeature
    def "New transfers can be done from the owners account"() {

    }

    @PendingFeature
    def "New transfers cannot be done from others owned accounts"() {

    }

    @PendingFeature
    def "A transfer can be canceled with a DELETE call if still in pending status"() {

    }

    @PendingFeature
    def "A transfer cannot be canceled with a DELETE call if in SETTLED status"() {

    }

    @PendingFeature
    def "A transfer cannot be canceled with a DELETE call if in REJECTED status"() {

    }

    @PendingFeature
    def "A transfer can me modified with a PUT call if in PENDING status"() {

    }

}
