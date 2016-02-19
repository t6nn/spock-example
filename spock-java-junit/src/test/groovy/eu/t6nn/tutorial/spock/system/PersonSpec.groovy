package eu.t6nn.tutorial.spock.system

import eu.t6nn.tutorial.spock.system.test.SpecThatHandlesMoney
import eu.t6nn.tutorial.spock.system.wallet.Money
import eu.t6nn.tutorial.spock.system.wallet.OutOfMoneyException
import eu.t6nn.tutorial.spock.system.wallet.Wallet
import spock.lang.Specification
/**
 * @author tonispi
 */
class PersonSpec extends Specification implements SpecThatHandlesMoney {

    def mockWallet = Mock(Wallet)
    def person = new Person(mockWallet)

    def "When giving money to a person, it is put into her wallet"() {
        when: "giving the person 5 USD"
        person.give(money(5, "USD"))

        then: "5 USD is put into the wallet"
        1 * mockWallet.put(money(5, "USD"))
    }

    def "When asked for money and the person is out of money, zero amount is returned"() {
        setup: "wallet is out of money"
        mockWallet.take(_) >> { throw new OutOfMoneyException(null) }

        when: "the person is asked for 10 EUR"
        def answer = person.ask(money(10, "EUR"))

        then:
        answer == Money.nothing(curr("EUR"))
    }

    def "When asked for money, the person takes that exact amount from her wallet"() {
        when: "the person is asked for 10 USD"
        person.ask(money(10, "USD"))

        then:
        1 * mockWallet.take(money(10, "USD"))
    }

    def "When asked for money, the same instance of money is returned"() {
        setup:
        def askedSum = money(20, "EUR")

        when:
        def answer = person.ask(askedSum)

        then:
        answer.is(askedSum)
    }

}