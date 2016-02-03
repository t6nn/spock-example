package eu.t6nn.tutorial.spock.system.wallet;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MagicWallet implements Wallet {

	private ConcurrentMap<Currency, Money> totals = new ConcurrentHashMap<>();

	@Override
	public void put(Money money) {
		totals.merge(money.getCurrency(),
				new Money(money.getAmount().multiply(BigDecimal.valueOf(2L)), money.getCurrency()), Money::add);
	}

	@Override
	public void take(Money money) throws OutOfMoneyException {
		// do nothing, it's a magic wallet that never runs out
	}

	@Override
	public Map<Currency, Money> totalsByCurrency() {
		return Collections.unmodifiableMap(new HashMap<>(totals));
	}

}
