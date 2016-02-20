package eu.t6nn.tutorial.spock.system

import eu.t6nn.tutorial.spock.system.test.SpecThatHandlesMoney
import eu.t6nn.tutorial.spock.system.wallet.Money
import eu.t6nn.tutorial.spock.system.wallet.OutOfMoneyException
import eu.t6nn.tutorial.spock.system.wallet.Wallet
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

/**
 * @author tonispi
 */
class PersonSpec extends Specification implements SpecThatHandlesMoney {

    def mockWallet = Mock(Wallet)

    @Subject
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

        then: "the person returns an empty amount"
        answer == Money.nothing(curr("EUR"))
    }

    def "When asked for money, the person takes that exact amount from her wallet"() {
        when: "the person is asked for 10 USD"
        person.ask(money(10, "USD"))

        then: "person takes 10 USD from her wallet"
        1 * mockWallet.take(money(10, "USD"))
    }

    @Ignore("This is way too technical")
    def "When asked for money, the same instance of money is returned"() {
        setup: "asking for 20 EUR"
        def askedSum = money(20, "EUR")

        when: "the person is asked for that money"
        def answer = person.ask(askedSum)

        then: "the money is requested from the wallet"
        1 * mockWallet.take(askedSum)

        and: "the answered amount is exactly the same instance of Money as was asked"
        answer.is(askedSum)
    }

}