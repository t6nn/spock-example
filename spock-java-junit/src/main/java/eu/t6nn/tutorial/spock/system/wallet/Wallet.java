package eu.t6nn.tutorial.spock.system.wallet;

import java.util.Currency;
import java.util.Map;

public interface Wallet {

	void put(Money money);

	void take(Money money) throws OutOfMoneyException;

	Map<Currency, Money> totalsByCurrency();
}
