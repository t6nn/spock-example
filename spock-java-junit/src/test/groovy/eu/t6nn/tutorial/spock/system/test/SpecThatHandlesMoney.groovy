package eu.t6nn.tutorial.spock.system.test

import eu.t6nn.tutorial.spock.system.wallet.Money
/**
 * Adds convenience methods to deal with money.
 *
 * @author tonispi
 */
trait SpecThatHandlesMoney {

    static final List DECORATED_CLASSES = [Integer, BigDecimal]

    def setupSpec() {
        DECORATED_CLASSES.forEach { numericClass ->
            numericClass.metaClass.getUSD = { -> money(delegate, "USD")}
            numericClass.metaClass.getEUR = { -> money(delegate, "EUR")}
        }
    }

    def cleanupSpec() {
        // Method removal perhaps available on Groovy 3.0: https://issues.apache.org/jira/browse/GROOVY-4189
        DECORATED_CLASSES.forEach { numericClass ->
            numericClass.metaClass.getUSD = {throw new MissingMethodException()}
            numericClass.metaClass.getEUR = {throw new MissingMethodException()}
        }
    }

    Money money(sum, currency) {
        if(sum instanceof BigDecimal) {
            new Money(sum, curr(currency))
        } else {
            new Money(BigDecimal.valueOf(sum), curr(currency))
        }
    }

    Currency curr(currency) {
        return Currency.getInstance(currency)
    }

}