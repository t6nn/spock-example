package eu.t6nn.tutorial.spock.system.wallet

import spock.lang.Specification
import spock.lang.Unroll

class NormalWalletSpec extends Specification {

	NormalWallet wallet = new NormalWallet()

	def "Empty wallet produces empty totals"() {
		when: "nothing happens to an empty wallet"

		then: "wallet totals report is empty"
		wallet.totalsByCurrency().isEmpty()
	}

	def "Taking from an empty wallet fails"() {
		when: "taking 5 USD from an empty wallet"
		wallet.take(money(5, "USD"))

		then: "an exception is thrown, as the wallet does not contain the required amount."
		thrown(OutOfMoneyException)
	}

	def "Negative amount cannot be put into the wallet"() {
		when: "putting a negative amount of money into the wallet"
		wallet.put(money(-3, "USD"))

		then: "an exception is thrown"
		thrown(IllegalArgumentException)
	}

	def "Negative amount cannot be taken from the wallet"() {
		when: "taking a negative amount of money from the wallet"
		wallet.take(money(-3, "USD"))

		then: "an exception is thrown"
		thrown(IllegalArgumentException)
	}

	@Unroll
	def "#sumOut USD can be taken from wallet if #sumIn USD was already put there"() {
		when: "putting $sumIn USD into the wallet"
		wallet.put(money(sumIn, "USD"))
		and: "taking $sumOut USD from the wallet"
		wallet.take(money(sumOut, "USD"))

		then: "no exceptions occur"
		notThrown(OutOfMoneyException)

		where:
		sumIn << [5, 8, 3.5]
		sumOut << [5, 7, 3.4]
	}

	def "A larger amount of money cannot be taken from wallet if a smaller amount has been put there"() {
		when: "putting $sumIn USD into the wallet"
		wallet.put(money(sumIn, "USD"))
		and: "taking $sumOut USD from the wallet"
		wallet.take(money(sumOut, "USD"))

		then: "an exception is thrown"
		thrown(OutOfMoneyException)

		where:
		sumIn | sumOut
		    5 | 20
		  0.2 | 0.21
		    9 | 9.1
	}

	Money money(sum, currency) {
		new Money(BigDecimal.valueOf(sum), Currency.getInstance(currency))
	}
}
