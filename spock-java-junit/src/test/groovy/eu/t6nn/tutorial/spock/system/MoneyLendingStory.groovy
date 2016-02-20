package eu.t6nn.tutorial.spock.system

import eu.t6nn.tutorial.spock.system.test.SpecThatHandlesMoney
import eu.t6nn.tutorial.spock.system.wallet.NormalWallet
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by t6nn on 2/20/16.
 */

@Narrative("""
As an Actor
When I give Money to a Person
I need to be able to retrieve the same amount or less of Money from the Person
""")
@Issue("https://github.com/t6nn/spock-examples/issues/1")
class MoneyLendingStory extends Specification implements SpecThatHandlesMoney {

    def person = new Person(new NormalWallet())

    @Unroll
    def "When giving #give.amount #give.currency to a Person and asking for #ask.amount #ask.currency back, I want #expected.amount #expected.currency back"()  {

        when: "given #give of money"
        person.give(give)

        and: "asking for #ask back"
        def returned = person.ask(ask)

        then: "expecting #expected to be returned"
        returned == expected

        where: "#give -> Person -> #expected"
              give      |        ask        ||    expected
        money(4, "USD") | money(4, "USD")   || money(4, "USD")
        money(4, "EUR") | money(3.9, "EUR") || money(3.9, "EUR")
    }
}
