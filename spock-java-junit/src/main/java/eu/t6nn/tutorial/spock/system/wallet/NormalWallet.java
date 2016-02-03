package eu.t6nn.tutorial.spock.system.wallet;

import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NormalWallet implements Wallet {

	private ConcurrentMap<Currency, Money> totals = new ConcurrentHashMap<>();

	@Override
	public void put(Money money) {
		totals.merge(money.getCurrency(), money, Money::add);
	}

	@Override
	public void take(Money money) throws OutOfMoneyException {
		try {
			totals.compute(money.getCurrency(), (c, m) -> {
				if (m == null || m.getAmount().compareTo(money.getAmount()) < 0) {
					throw new IllegalArgumentException(new OutOfMoneyException(m));
				} else {
					return m.sub(money);
				}
			});
		} catch (IllegalArgumentException e) {
			if (e.getCause() instanceof OutOfMoneyException) {
				throw (OutOfMoneyException) e.getCause();
			}
		}
	}

	@Override
	public Map<Currency, Money> totalsByCurrency() {
		return Collections.unmodifiableMap(new HashMap<>(totals));
	}

}
