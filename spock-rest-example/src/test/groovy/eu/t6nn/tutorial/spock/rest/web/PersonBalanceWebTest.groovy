package eu.t6nn.tutorial.spock.rest.web

import eu.t6nn.tutorial.spock.rest.SpockRestExampleApplication
import eu.t6nn.tutorial.spock.rest.web.def.PersonMainPage
import geb.Configuration
import geb.spock.GebSpec
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.Stepwise
/**
 * @author tonispi
 */
@IgnoreIf({!os.windows})
@SpringApplicationConfiguration(classes = SpockRestExampleApplication.class)
@WebIntegrationTest("server.port:0")
@Stepwise
@DirtiesContext
class PersonBalanceWebTest extends GebSpec {

    @Value('${local.server.port}')
    int port

    def "Initially, no balances are shown for a person" () {
        when: "going to the main page"
        to PersonMainPage

        then: "balance list is empty"
        balances.balanceItems.size() == 0
    }

    @Ignore("Not yet implemented")
    def "When adding 2 EUR to the balance, the balance is shown"() {
        when: "going to the main page"
        to PersonMainPage

        and:
        txForm.amount.enter("2")
        txForm.amount.give("EUR")

        then:
        true
    }

    Configuration createConf() {
        def configuration = super.createConf()
        configuration.baseUrl = "http://localhost:$port"
        configuration
    }

}