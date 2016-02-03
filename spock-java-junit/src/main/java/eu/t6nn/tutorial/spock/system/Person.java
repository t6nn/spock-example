package eu.t6nn.tutorial.spock.system;

import java.util.ArrayList;
import java.util.List;

import eu.t6nn.tutorial.spock.system.wallet.Money;
import eu.t6nn.tutorial.spock.system.wallet.Wallet;

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

	public Iterable<String> reportBalance() {
		List<String> balances = new ArrayList<>();
		wallet.totalsByCurrency().forEach((c, m) -> balances.add(c + ":" + m.getAmount()));
		return balances;
	}

}
