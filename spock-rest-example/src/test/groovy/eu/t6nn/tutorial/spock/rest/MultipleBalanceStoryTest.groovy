package eu.t6nn.tutorial.spock.rest

import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

/**
 * Named *Test because of Spring Boot default surefire configuration.
 * Created by t6nn on 2/23/16.
 */
@SpringApplicationConfiguration(classes = SpockRestExampleApplication.class)
@WebIntegrationTest("server.port:0")
@Stepwise
@DirtiesContext
class MultipleBalanceStoryTest extends Specification {

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

    def "When giving a person money of different currencies, it is added to a separate balance"() {
        when: "adding #amount #currency to the person's balance"
        post('/person/give', [currency: currency, amount: amount])

        and: "getting the balances"
        def balances = endpoint().get([path: '/person/balances'])

        then: "balances contain #expected"
        balances.data.contains expected

        where:
        amount | currency || expected
        5      | 'USD'    || [currency: 'USD', amount: '5']
        10     | 'EUR'    || [currency: 'EUR', amount: '10']
        2      | 'EUR'    || [currency: 'EUR', amount: '12']
        3.2    | 'USD'    || [currency: 'USD', amount: '8.2']
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
