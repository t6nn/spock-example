package eu.t6nn.tutorial.spock.system.wallet;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

	private final BigDecimal amount;
	private final Currency currency;

	public Money(BigDecimal amount, Currency currency) {
		notNull(amount, "amount");
		notNull(currency, "currency");
		this.amount = amount;
		this.currency = currency;
	}

	public static Money nothing(Currency currency) {
		return new Money(BigDecimal.ZERO, currency);
	}

	private void notNull(Object value, String argName) {
		if (value == null) {
			throw new IllegalArgumentException(argName + " cannot be null.");
		}
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money add(Money addend) {
		if (!this.currency.equals(addend.currency)) {
			throw new IllegalArgumentException("The currencies of " + this + " and " + addend + " do not match.");
		}
		return new Money(this.amount.add(addend.amount), this.currency);
	}

	public Money sub(Money subtrahend) {
		return add(subtrahend.neg());
	}

	public Money neg() {
		return new Money(amount.negate(), currency);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Money money = (Money) o;

		if (!amount.equals(money.amount)) return false;
		return currency.equals(money.currency);

	}

	@Override
	public int hashCode() {
		int result = amount.hashCode();
		result = 31 * result + currency.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return amount + " " + currency;
	}
}
