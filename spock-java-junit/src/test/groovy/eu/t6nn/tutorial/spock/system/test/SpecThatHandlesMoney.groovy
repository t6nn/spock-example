package eu.t6nn.tutorial.spock.system.test

import eu.t6nn.tutorial.spock.system.wallet.Money

/**
 * Adds convenience methods to deal with money.
 *
 * @author tonispi
 */
trait SpecThatHandlesMoney {

    Money money(sum, currency) {
        new Money(BigDecimal.valueOf(sum), curr(currency))
    }

    Currency curr(currency) {
        return Currency.getInstance(currency)
    }
}