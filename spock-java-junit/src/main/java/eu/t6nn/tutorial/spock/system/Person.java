package eu.t6nn.tutorial.spock.system;

import eu.t6nn.tutorial.spock.system.wallet.Money;
import eu.t6nn.tutorial.spock.system.wallet.OutOfMoneyException;
import eu.t6nn.tutorial.spock.system.wallet.Wallet;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private final Wallet wallet;

	public Person(Wallet wallet) {
		if (wallet == null) {
			throw new IllegalArgumentException("A person MUST have a wallet.");
		}
		this.wallet = wallet;
	}

	public void give(Money money) {
		wallet.put(money);
	}

	public Money ask(Money money) {
		try {
			wallet.take(money);
			return money;
		} catch (OutOfMoneyException e) {
			return Money.nothing(money.getCurrency());
		}
	}

	Iterable<String> reportBalances() {
		List<String> balances = new ArrayList<>();
		wallet.checkBalances().forEach((c, m) -> balances.add(c + ":" + m.getAmount()));
		return balances;
	}

}
