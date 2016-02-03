package eu.t6nn.tutorial.spock.system.wallet

import spock.lang.Specification

class NormalWalletSpec extends Specification {

	NormalWallet wallet = new NormalWallet()

	def "Empty wallet produces empty totals"() {
		when: "nothing happens to an empty wallet"

		then: "wallet totals report is empty"
		wallet.totalsByCurrency().isEmpty()
	}

	def "Taking from an empty wallet fails"() {
		when: "taking money from an empty wallet"
		wallet.take(money(5, "USD"))

		then:
		thrown(OutOfMoneyException)
	}

	def "Money of specific currency can be taken from wallet if the same amount or more was already put there"() {
		when: "putting $sumIn USD into the wallet"
		wallet.put(money(sumIn, "USD"))
		and: "taking $sumOut USD from the wallet"
		wallet.take(money(sumOut, "USD"))

		then: notThrown(OutOfMoneyException)

		where:
		sumIn << [5, 8, 3.5]
		sumOut << [5, 7, 3.4]
	}

	Money money(sum, currency) {
		new Money(BigDecimal.valueOf(sum), Currency.getInstance(currency))
	}
}
