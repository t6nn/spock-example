package eu.t6nn.tutorial.spock.system.wallet;

public class OutOfMoneyException extends Exception {

	private static final long serialVersionUID = 7765702835039292539L;

	private final Money remainingBalance;

	public OutOfMoneyException(Money remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public Money getRemainingBalance() {
		return remainingBalance;
	}
}
