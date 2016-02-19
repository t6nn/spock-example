package eu.t6nn.tutorial.spock.system.wallet;

import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MagicWallet implements Wallet {

	private ConcurrentMap<Currency, Money> balances = new ConcurrentHashMap<>();

	@Override
	public void put( Money money ) {
		// add twice as much money!
		balances.merge( money.getCurrency(), money.add( money ), Money::add );
	}

	@Override
	public void take( Money money ) throws OutOfMoneyException {
		// do nothing, it's a magic wallet that never runs out
	}

	@Override
	public Map<Currency, Money> checkBalances() {
		return Collections.unmodifiableMap(balances);
	}

}
