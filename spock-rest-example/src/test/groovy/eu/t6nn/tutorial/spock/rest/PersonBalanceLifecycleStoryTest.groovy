package eu.t6nn.tutorial.spock.rest

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * Named *Test because of Spring Boot default surefire configuration.
 * Created by t6nn on 2/23/16.
 */
@SpringApplicationConfiguration(classes = SpockRestExampleApplication.class)
@WebIntegrationTest("server.port:0")
@Stepwise
@DirtiesContext
class PersonBalanceLifecycleStoryTest extends Specification {

    @Autowired
    EmbeddedWebApplicationContext server

    // Single quotes in order not to confuse Groovy here
    @Value('${local.server.port}')
    int port

    def "Initially, the list of person balances is empty"() {
        when: "requesting person balances"
        def response = endpoint().get([path: '/person/balances'])

        then: "response is in correct format"
        with(response) {
            status == 200
            contentType == 'application/json'

        }
        and: "response body is an empty list"
        response.data == []
    }

    def "When giving a person money, it is added to her balance"() {
        when: "adding 5 USD to the person's balance"
        post('/person/give', [currency: 'USD', amount: '5'])

        and: "getting the balances"
        def balances = endpoint().get([path: '/person/balances'])

        then: "a new balance is created with the given amount"
        balances.data == [[currency: 'USD', amount: '5']]
    }

    def "When adding additional money of the same currency, the balance is increased" () {
        when: "adding 10 USD to the person's balance"
        post('/person/give', [currency: 'USD', amount: '10'])

        and: "getting the balances"
        def balances = endpoint().get([path: '/person/balances'])

        then: "the given amount is added to the current balance"
        balances.data == [[currency: 'USD', amount: '15']]
    }

    def "When taking from an existing balance, the balance is reduced by the given amount" () {
        when: "taking 3 USD from the person's balance"
        post('/person/ask', [currency: 'USD', amount: '3'])

        and: "getting the balances"
        def balances = endpoint().get([path: '/person/balances'])

        then: "the asked amount is deducted from the current balance"
        balances.data == [[currency: 'USD', amount: '12']]
    }

    def endpoint() {
        return new RESTClient("http://localhost:$port")
    }

    def post(toPath, withBody) {
        endpoint().request(Method.POST, ContentType.JSON, { req ->
            uri.path = toPath
            body = withBody
        });
    }

}
