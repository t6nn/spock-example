package eu.t6nn.tutorial.spock.rest.model;

import eu.t6nn.tutorial.spock.system.wallet.Money;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author tonispi
 */
public class MoneyOnWire {

    private String currency;
    private String amount;

    public MoneyOnWire() {
    }

    public MoneyOnWire(Money money) {
        this.currency = money.getCurrency().getCurrencyCode();
        this.amount = money.getAmount().toPlainString();
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public Money parseMoney() {
        return new Money(new BigDecimal(amount), Currency.getInstance(currency));
    }
}
